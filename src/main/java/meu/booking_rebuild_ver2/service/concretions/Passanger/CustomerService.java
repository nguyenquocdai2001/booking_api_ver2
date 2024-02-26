package meu.booking_rebuild_ver2.service.concretions.Passanger;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.GenericResponseException;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Admin.Mapper.CustomerMapper;
import meu.booking_rebuild_ver2.model.Admin.Mapper.LoyaltyMapper;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.request.LoginRequest;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import meu.booking_rebuild_ver2.service.impl.CustomerDetailsImplement;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Author: Nguyễn Minh Tâm
 * BS-3
 */

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @Autowired
    private LoyaltyMapper loyaltyMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ILoyaltyService loyaltyService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    public static final String CLIENTID = "CLIENT_ID";
    @Autowired
    private StatusRepository statusRepository;
    // The phone number regex to check phone valid
    private static final String PHONE_NUMBER_REGEX = "^(\\+?84|0)([3|5|7|8|9])+([0-9]{8})$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public CustomerService(BCryptPasswordEncoder passwordEncoder,CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // The function to add new customer
    @Transactional
    @Override
    public GenericResponse addCustomer(CustomerRequest request) throws GenericResponseException {

        if(phoneValid(request.getPhone())){
            throw new GenericResponseException(Constants.MESSAGE_PHONE_FORMAT_WRONG);
        }
        try{
            Customer model = customerRepository.findCustomerByPhone(request.getPhone());
            if(model == null){
                Customer requestCustomer = new Customer(request.getName(), request.getPhone(), request.getPassword());
                Loyalty loyalty = loyaltyMapper.toModel(loyaltyService.getLoyaltyByPrice());
                requestCustomer.setLoyalty(loyalty);
                Status status = statusRepository.findStatusById(request.getStatusId());
                requestCustomer.setStatus(status);
                requestCustomer.setNumberOfTrips(0);
                requestCustomer.setPassword(passwordEncoder.encode(requestCustomer.getPassword()));
                customerRepository.save(requestCustomer);
                return new GenericResponse(Constants.MESSAGE_ADDED_CUSTOMER_SUCCESSFULLY);
            }
            else{
                throw new GenericResponseException(Constants.MESSAGE_ADD_CUSTOMER_FAILED);
            }
        }
        catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    // The function to get customer by id customer
    @Override
    public CustomerResponse getCustomerById(UUID id) throws NotFoundException {
        try{
            Customer model = customerRepository.findCustomerById(id);
            if(model == null){
                throw new NotFoundException("Can not get the customer with id: " + id);
            }
            CustomerDTO response = customerMapper.toDTO(model);
            return new CustomerResponse(Constants.MESSAGE_GET_SUCCESSFULL+"Get the customer sucessfully",true,response);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    // The function will get the list customer by phone. It has no pagination
    @Override
    public CustomerResponse getCustomersByPhone(String phone) throws NotFoundException {
        CustomerResponse listResponse = new CustomerResponse();
        if(phone.length() < 5) {
            return listResponse;
        }
        List<Customer> models = customerRepository.getCustomersByPhone(phone);
         listResponse.setListCustomer(getListResponse(models));
         return listResponse;
    }
    // The function will get the list customer. It has no pagination
    @Override
    public CustomerResponse getCustomerByLoyalty(UUID idLoyalty) throws NotFoundException {
        Optional<Loyalty> loyalty = loyaltyRepository.findById(idLoyalty);
        if(loyalty.isEmpty()){
            throw new NotFoundException("Can not get the loyalty with id: " + idLoyalty);
        }
        List<Customer> models = customerRepository.getCustomersByLoyalty_Id(idLoyalty);
        CustomerResponse listResponse = new CustomerResponse();
        listResponse.setListCustomer(getListResponse(models));
        return listResponse;
    }

    @Override
    public CustomerResponse getCustomerByPhone(String phone) {
        if(phone.length()<10){
            return new CustomerResponse("Can not get the user",false);
        }
        else{
            Customer model = customerRepository.findCustomerByPhone(phone);
            if(model != null){
                CustomerDTO modelDTO = customerMapper.toDTO(model);
                return new CustomerResponse("Get the user success", true, modelDTO);
            }
            else{
                return new CustomerResponse("The customer has not been exit", false);
            }

        }

    }

    // The function will return the list from Customer to CustomerDTO
    private  List<CustomerDTO> getListResponse(List<Customer> models){
        List<CustomerDTO> reponseList = new ArrayList<>();
        for(Customer model: models){
            CustomerDTO response = customerMapper.toDTO(model);
            reponseList.add(response);
        }
        return reponseList;
    }
    // Function to update customer with total paid
    @Override
    public GenericResponse updateCustomer(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseException {
        if(phoneValid(request.getPhone())){
            throw new GenericResponseException(Constants.MESSAGE_PHONE_FORMAT_WRONG);
        }
        Customer modelPhone = customerRepository.findCustomerByPhone(request.getPhone());
        Customer model = customerRepository.findCustomerById(id);
        if(modelPhone != null){
            if(model != null){
                if(modelPhone.getId() != model.getId()){
                    throw new GenericResponseException(Constants.MESSAGE_DUPLICATE_PHONE_CUSTOMER);
                }
            }
            else{
                throw new NotFoundException("Can not get the customer with id: " + id);
            }
        }
        if(model == null) throw new NotFoundException("Can not get the customer with id: " + id);
        try{
            model.setName(request.getName());
            model.setPhone(request.getPhone());
            System.out.println(request);
            Optional<Loyalty> loyalty = loyaltyRepository.getLoyaltyByPrice(request.getTotalPaid());
            model.setLoyalty(loyalty.get());
            if(request.getStatus() != null){
                Status status = statusRepository.findStatusById(UUID.fromString(request.getStatus()));
                if(status != null) model.setStatus(status);
            }
            model.setCreatedAt(model.getCreatedAt());
            model.setNumberOfTrips(request.getNumberOfTrips());
            model.setUpdatedAt(Instant.now());
            customerRepository.save(model);
            return new GenericResponse(Constants.MESSAGE_UPDATED_CUSTOMER_SUCCESSFULLY);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    // Function to update by set rank or loyalty id. It will prioritize set the loyalty by id
    @Override
    public GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseException {
        boolean isSatisfied = false;
        if(phoneValid(request.getPhone())){
            throw new GenericResponseException(Constants.MESSAGE_PHONE_FORMAT_WRONG + "Can not update with that number phone. ");
        }
        Customer modelPhone = customerRepository.findCustomerByPhone(request.getPhone());
        Customer model = customerRepository.findCustomerById(id);
        if(modelPhone != null){
            if(model != null){
                if(modelPhone.getId() != model.getId()){
                    throw new GenericResponseException(Constants.MESSAGE_DUPLICATE_PHONE_CUSTOMER);
                }
            }
            else{
                throw new NotFoundException("Can not get the customer with id: " + id);
            }
        }
        if(model == null) throw new NotFoundException("Can not get the customer with id: " + id);
        try{
            model.setName(request.getName());
            model.setPhone(request.getPhone());
            String rank = request.getLoyaltyDto().getRank();
            UUID id_loyalty = request.getLoyaltyDto().getId();
            model.setLastUpdated(true);
            if(id_loyalty != null){
                Optional<Loyalty> loyalty = loyaltyRepository.findById(id_loyalty);
                if(loyalty.isPresent()){
                    model.setLoyalty(loyalty.get());
                    isSatisfied = true;
                }
                else{
                    throw new NotFoundException("Can not get the loyalty with id: " + id + ". Can not update customer!" );
                }
            }
             if((rank != null) && (!isSatisfied)){
                Optional<Loyalty> loyalty = loyaltyRepository.findByRank(rank);
                if(loyalty.isPresent()){
                    model.setLoyalty(loyalty.get());
                    isSatisfied = true;
                }
                else{
                    throw new NotFoundException("Can not get the loyalty with rank: "+ rank + ". Can not update customer!");
                }

            }
            if(!isSatisfied){
                throw new GenericResponseException("The request is not valid");
            }
            Status status = statusRepository.findStatusById(UUID.fromString(request.getStatus()));
            if(status != null) model.setStatus(status);
            model.setNumberOfTrips(request.getNumberOfTrips());
            model.setUpdatedAt(Instant.now());
            customerRepository.save(model);
            return new GenericResponse(Constants.MESSAGE_UPDATED_CUSTOMER_SUCCESSFULLY);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    // Function to getCustomer by phone. It has pagination
    @Override
    public Page<CustomerResponse> getCustomerByPhoneWithPage(String phone, Integer page) throws NotFoundException {
        if (phone.length() < 5) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);
        }
        try {
            Page<CustomerDTO> listCustomers = customerRepository.getCustomersByPhoneAsPage(phone, PageRequest.of(Math.abs(page), 10));
            return convertToCustomerResponse(listCustomers, page);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    // The function to get the list customer by loyalty. It has pagination
    @Override
    public Page<CustomerResponse> getCustomerByLoyaltyWithPage(UUID IdLoyalty, Integer page) throws NotFoundException {
        try{
            Page<CustomerDTO> customerDTOPage = customerRepository.getCustomersByLoyaltyAsPage(IdLoyalty, PageRequest.of(Math.abs(page), 10));
            // Create and return with Page Object with only CustomerResponse
            return convertToCustomerResponse(customerDTOPage, page);
        }
        catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    // Function to update customer to economy when loyalty is deleted
    @Override
    public void handleUpdateCustomerWhenLoyaltyDelete(List<CustomerDTO> customerDTOS) throws GenericResponseException, NotFoundException {
            customerDTOS.stream()
                    .peek(customer -> {
                        try {
                            customer.setIdLoyalty(loyaltyService.getLoyaltyByPrice().getId());
                        } catch (GenericResponseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(customerDTO -> {
                        try {
                            return customerMapper.toModel(customerDTO);
                        } catch (NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .peek(customerRepository::save)
                    .forEach(System.out::println);

    }
    //Function to delete Customer by id customer
    @Override
    public GenericResponse deleteCustomerById(UUID id) throws NotFoundException {
        Customer model = customerRepository.findCustomerById(id);
        if(model == null){
            throw new NotFoundException("Can not get the customer with id: " + id);
        }
        customerRepository.delete(model);
        return new GenericResponse(Constants.MESSAGE_DELETED_SUCCESS + "The customer with id: " + id + "has been deleted successfully");
    }

    @Override
    public LoginResponse customerLoginHandle(String phone, String password) throws NotFoundException, GenericResponseException {
        try{
            Customer customer = customerRepository.findCustomerByPhone(phone);
            if(customer == null){
                throw new NotFoundException("Can not get the user with phone: "+ phone);
            }
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getSession();
            session.setAttribute("CLIENT_ID", customer.getId());
            CustomerDTO  customerDTO = customerMapper.toDTO(customer);

            if(passwordEncoder.matches(password, customer.getPassword())){
                String jwt = jwtUtils.createToken(phone, customer.getUserRole());
                LoginResponse response = new LoginResponse(Constants.MESSAGE_LOGIN_SUCCESS, jwt,Collections.singletonList(customer.getUserRole()),customerDTO );
                return response;
            }
            else{
                throw new GenericResponseException(Constants.MESSAGE_INVALID_PASSWORD);
            }
        }
        catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public UserDetails loadCustomerByPhone(String phone) {
        Customer customer = customerRepository.findCustomerByPhone(phone);
        if(customer == null){
            throw new UsernameNotFoundException(Constants.MESSAGE_GET_NOT_FOUND);
        }
        return new CustomerDetailsImplement(customer);
    }

    //Function to check valid phone with regex phone
    protected static boolean phoneValid(String phone){
        Matcher matcher = pattern.matcher(phone);
        return !matcher.matches();
    }
    //Function to Covert from CustomerDTO to CustomerResponse
    private Page<CustomerResponse> convertToCustomerResponse(Page<CustomerDTO> customerDTO, int page) throws NotFoundException {
        CustomerResponse customerResponse = new CustomerResponse(Constants.MESSAGE_GET_SUCCESSFULL+"Get the list customer with pagination sucessfully", true, customerDTO.getContent());
        if(customerResponse.getListCustomer().isEmpty()){
            throw new NotFoundException(" Customer In this page");
        }
        return new PageImpl<>(
                Collections.singletonList(customerResponse),
                PageRequest.of(Math.abs(page), 10),
                customerDTO.getTotalElements()
        );
    }
}
