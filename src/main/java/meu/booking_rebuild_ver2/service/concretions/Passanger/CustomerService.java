package meu.booking_rebuild_ver2.service.concretions.Passanger;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Admin.Mapper.CustomerMapper;
import meu.booking_rebuild_ver2.model.Admin.Mapper.LoyaltyMapper;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    private IUserService userService;

    @Autowired
    private StatusRepository statusRepository;
    // The phone number regex to check phone valid
    private static final String PHONE_NUMBER_REGEX = "^(\\+?84|0)([3|5|7|8|9])+([0-9]{8})$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    // The function to add new customer
    @Override
    public GenericResponse addCustomer(CustomerRequest request) throws GenericResponseExceptionHandler {

        if(phoneValid(request.getPhone())){
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_PHONE_FORMAT_WRONG);
        }
        try{
            Customer model = customerRepository.findCustomerByPhone(request.getPhone());
            if(model == null){
                Customer requestCustomer = new Customer(request.getName(), request.getPhone());
                Loyalty loyalty = loyaltyMapper.toModel(loyaltyService.getLoyaltyByPrice());
                requestCustomer.setLoyalty(loyalty);
                Status status = statusRepository.findStatusById(request.getStatusId());
                requestCustomer.setStatus(status);
                requestCustomer.setNumberOfTrips(0);
                customerRepository.save(requestCustomer);
                return new GenericResponse(Constants.MESSAGE_ADDED_CUSTOMER_SUCCESSFULLY);
            }
            else{
                throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_CUSTOMER_FAILED);
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
                throw new NotFoundException();
            }
            CustomerDTO response = customerMapper.toDTO(model);
            return new CustomerResponse(response);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    // The function will get the list customer by phone. It has no pagination
    @Override
    public CustomerResponse getCustomerByPhone(String phone) throws NotFoundException {
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
            throw new NotFoundException();
        }
        List<Customer> models = customerRepository.getCustomersByLoyalty_Id(idLoyalty);
        CustomerResponse listResponse = new CustomerResponse();
        listResponse.setListCustomer(getListResponse(models));
        return listResponse;
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
    public GenericResponse updateCustomer(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler {
        if(phoneValid(request.getPhone())){
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_PHONE_FORMAT_WRONG);
        }
        Customer modelPhone = customerRepository.findCustomerByPhone(request.getPhone());
        Customer model = customerRepository.findCustomerById(id);
        if(modelPhone != null){
            if(model != null){
                if(modelPhone.getId() != model.getId()){
                    throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_CUSTOMER_FAILED);
                }
            }
            else{
                throw new NotFoundException();
            }
        }
        if(model == null) throw new NotFoundException();
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

            model.setNumberOfTrips(request.getNumberOfTrips());
            customerRepository.save(model);
            return new GenericResponse(Constants.MESSAGE_UPDATED_CUSTOMER_SUCCESSFULLY);
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
    // Function to update by set rank or loyalty id. It will prioritize set the loyalty by id
    @Override
    public GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler {
        boolean isSatisfied = false;
        if(phoneValid(request.getPhone())){
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_PHONE_FORMAT_WRONG);
        }
        Customer modelPhone = customerRepository.findCustomerByPhone(request.getPhone());
        Customer model = customerRepository.findCustomerById(id);
        if(modelPhone != null){
            if(model != null){
                if(modelPhone.getId() != model.getId()){
                    throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_CUSTOMER_FAILED);
                }
            }
            else{
                throw new NotFoundException();
            }
        }
        if(model == null) throw new NotFoundException();
        try{
            model.setName(request.getName());
            model.setPhone(request.getPhone());
            String rank = request.getLoyaltyDto().getRank();
            UUID id_loyalty = request.getLoyaltyDto().getId();
            model.setLastUpdated(true);
            if(id_loyalty != null){
                Optional<Loyalty> loyalty = loyaltyRepository.findById(id_loyalty);

                model.setLoyalty(loyalty.get());
                isSatisfied = true;
            }

            else if((rank != null) && (!isSatisfied)){
                Optional<Loyalty> loyalty = loyaltyRepository.findByRank(rank);
                loyalty.ifPresent(model::setLoyalty);
            }
            else{
                throw new GenericResponseExceptionHandler("The request is not valid");
            }
            Status status = statusRepository.findStatusById(UUID.fromString(request.getStatus()));
            if(status != null) model.setStatus(status);
            model.setNumberOfTrips(request.getNumberOfTrips());
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
            // Chuyển đổi danh sách khách hàng sang CustomerResponse
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
    public void handleUpdateCustomerWhenLoyaltyDelete(List<CustomerDTO> customerDTOS) throws GenericResponseExceptionHandler, NotFoundException {
        for(CustomerDTO customer : customerDTOS){
            customer.setIdLoyalty(loyaltyService.getLoyaltyByPrice().getId());
            Customer modelCustomer = customerMapper.toModel(customer);
            System.out.println(modelCustomer);
            customerRepository.save(modelCustomer);
        }
    }
    //Function to delete Customer by id customer
    @Override
    public GenericResponse deleteCustomerById(UUID id) throws NotFoundException {
        Customer model = customerRepository.findCustomerById(id);
        if(model == null){
            throw new NotFoundException();
        }
        customerRepository.delete(model);
        return new GenericResponse(Constants.MESSAGE_DELETED_SUCCESSFULLY);
    }
    //Function to check valid phone with regex phone
    protected static boolean phoneValid(String phone){
        Matcher matcher = pattern.matcher(phone);
        return !matcher.matches();
    }
    //Function to Covert from CustomerDTO to CustomerResponse
    private Page<CustomerResponse> convertToCustomerResponse(Page<CustomerDTO> customerDTO, int page) throws NotFoundException {
        CustomerResponse customerResponse = new CustomerResponse(customerDTO.getContent());
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
