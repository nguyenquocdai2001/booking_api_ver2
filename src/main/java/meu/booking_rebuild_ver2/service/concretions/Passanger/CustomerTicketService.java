package meu.booking_rebuild_ver2.service.concretions.Passanger;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.*;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerTicketDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.CustomerTicketMapper;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.*;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerTicketRepository;
import meu.booking_rebuild_ver2.repository.Passanger.LocationRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Passanger.CustomerTicketResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
@Service
public class CustomerTicketService implements ICustomerTicketService{

    @Autowired
    CustomerTicketRepository customerTicketRepo;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    RoutesRepository routesRepo;
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BusSeatRepository busSeatRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    RoutesTimeRepository routesTimeRepository;
    @Autowired
    private UserID user ;

    @Override
    public CustomerTicketResponse createCustomerTicket(CustomerTicketDTO customerTicketDTO) {
        try {
            CustomerTicketResponse response;
            if(!checkData(customerTicketDTO)){
                response = new CustomerTicketResponse(Constants.MESSAGE_INVALID_DATA, false , customerTicketDTO);
            }else {
                customerTicketDTO.setIdUserConfig(user.getUserValue().getId());
                customerTicketDTO.setTicketCode(ticketCode(customerTicketDTO));
                CustomerTicketModel model = CustomerTicketMapper.dtoToCustomerTickets(customerTicketDTO);
                Status status = statusRepository.findStatusById(customerTicketDTO.getIdStatus());
                model.setStatus(status);
                customerTicketRepo.save(model);
                response = new CustomerTicketResponse(Constants.MESSAGE_STATUS_ADD_CUSTOMERTICKET_SUCCESS, true, customerTicketDTO);
            }
            return response;
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public CustomerTicketResponse getAllCustomerTicket() {
        List<CustomerTicketDTO> list = customerTicketRepo.findAll()
                .stream()
                .map(CustomerTicketMapper::customerTicketDTO)
                .toList();
        return new CustomerTicketResponse(Constants.MESSAGE_STATUS_GET_ALL_CUSTOMERTICKET_SUCCESS, true, list);
    }

    @Override
    public CustomerTicketResponse updateCustomerTicket(CustomerTicketDTO customerTicketDTO) {
        CustomerTicketModel updatemodel = customerTicketRepo.findCustomerTicketModelById(customerTicketDTO.getId());

        if(updatemodel != null && checkData(customerTicketDTO)) {
            updatemodel.setIdCustomer(new Customer(customerTicketDTO.getIdCustomer().getId()));
            updatemodel.setIdBusSeat(new BusSeat(customerTicketDTO.getIdBusSeat().getId())) ;
            updatemodel.setDepartureLocation(new Location(customerTicketDTO.getDepartureLocation().getId()));
            updatemodel.setDestinationLocation(new Location(customerTicketDTO.getDestinationLocation().getId()));
            updatemodel.setIdPrice(new PriceModel(customerTicketDTO.getIdPrice().getId()));
            updatemodel.setPaid(customerTicketDTO.isPaid());
            updatemodel.setTicketCode(ticketCode(customerTicketDTO));
            Status status = statusRepository.findStatusById(customerTicketDTO.getIdStatus());
            updatemodel.setStatus(status);
            updatemodel.setUpdatedAt(ZonedDateTime.now());
            updatemodel.setIdUserConfig(user.getUserValue());
            customerTicketRepo.save(updatemodel);
            return new CustomerTicketResponse(Constants.MESSAGE_UPDATE_CUSTOMERTICKET_SUCCESS, true,customerTicketDTO);

        }
        return new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public CustomerTicketResponse findByID(UUID id) {
        if(customerTicketRepo.existsById(id)) {
            CustomerTicketModel model = customerTicketRepo.findCustomerTicketModelById(id);
            CustomerTicketDTO modelDTO = CustomerTicketMapper.customerTicketDTO(model);
            return new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS, true, modelDTO);
        }
        return new CustomerTicketResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public CustomerTicketResponse deleteById(UUID id) {
        CustomerTicketResponse response;
        if(!customerTicketRepo.existsById(id)){
            response = new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND,true );
        }
        else{
            customerTicketRepo.deleteById(id);
            response = new CustomerTicketResponse("CustomerTicket"+ Constants.MESSAGE_DELETED_SUCCESS,true);
        }
        return response;
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByStatus(UUID id) {
        if(statusRepository.existsById(id)){
         List<CustomerTicketDTO> list = customerTicketRepo.getCustomerTicketByStatus(id)
                 .stream()
                 .map(CustomerTicketMapper::customerTicketDTO)
                 .toList();
            if(!list.isEmpty()){
                return new CustomerTicketResponse(Constants.MESSAGE_STATUS_GET_ALL_CUSTOMERTICKET_SUCCESS, true, list);
            }
        }
        return new CustomerTicketResponse("Status "+Constants.MESSAGE_ID_NOT_FOUND, false);

    }

    @Override
    public CustomerTicketResponse getCustomerTicketByTime(UUID id) {
        try{
            CustomerTicketResponse response;
            if(!timeRepository.existsById(id)){
                response = new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, true);
            }else{
                List<CustomerTicketDTO> customerTicketDTOList = customerTicketRepo.getCustomerTicketByTime(id)
                        .stream()
                        .map(CustomerTicketMapper::customerTicketDTO).toList();
                response = new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS,true ,customerTicketDTOList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByRoutes(UUID id) {
        try{
            CustomerTicketResponse response;
            if(!routesRepo.existsById(id)){
                response = new CustomerTicketResponse("Routes"+ Constants.MESSAGE_ID_NOT_FOUND, true);
            }else{
                List<CustomerTicketDTO> customerTicketList = customerTicketRepo.getCustomerTicketByRoute(id)
                        .stream()
                        .map(CustomerTicketMapper::customerTicketDTO)
                        .toList();
                response = new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS,true ,customerTicketList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByRoutesTime(UUID id) {
        try{
            CustomerTicketResponse response;
            if(!routesTimeRepository.existsById(id)){
                response = new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, true);
            }else{
                List<CustomerTicketDTO> customerTicketDTOList = customerTicketRepo.getCustomerTicketByRouteTime(id)
                        .stream()
                        .map(CustomerTicketMapper::customerTicketDTO).toList();
                response = new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS,true ,customerTicketDTOList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByTicketCode(String ticketCode) {

            CustomerTicketModel model = customerTicketRepo.getCustomerTicketByTicketCode(ticketCode);
            if(model != null) {
                CustomerTicketDTO modelDTO = CustomerTicketMapper.customerTicketDTO(model);
                return new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS, true, modelDTO);
            }
        return new CustomerTicketResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByBusSeat(UUID id) {
        CustomerTicketModel model = customerTicketRepo.getCustomerTicketByBusSeat(id);
        if(model != null) {
            CustomerTicketDTO modelDTO = CustomerTicketMapper.customerTicketDTO(model);
            return new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS, true, modelDTO);
        }
        return new CustomerTicketResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByDeparture(UUID id) {
        try{
            CustomerTicketResponse response;
            if(!locationRepository.existsById(id)){
                response = new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, true);
            }else{
                List<CustomerTicketDTO> customerTicketDTOList = customerTicketRepo.getCustomerTicketByDepartureLocation(id)
                        .stream()
                        .map(CustomerTicketMapper::customerTicketDTO).toList();
                response = new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS,true ,customerTicketDTOList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerTicketResponse getCustomerTicketByDestination(UUID id) {
        try{
            CustomerTicketResponse response;
            if(!locationRepository.existsById(id)){
                response = new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, true);
            }else{
                List<CustomerTicketDTO> customerTicketDTOList = customerTicketRepo.getCustomerTicketByDestinationLocation(id)
                        .stream()
                        .map(CustomerTicketMapper::customerTicketDTO).toList();
                response = new CustomerTicketResponse(Constants.MESSAGE_CUSTOMERTICKET_FIND_SUCCESS,true ,customerTicketDTOList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CustomerTicketResponse setPayForTicket(UUID id) {
        CustomerTicketModel updatemodel = customerTicketRepo.findCustomerTicketModelById(id);
        if(updatemodel != null) {
            updatemodel.setPaid(true);
            updatemodel.setUpdatedAt(ZonedDateTime.now());
            updatemodel.setIdUserConfig(user.getUserValue());
            customerTicketRepo.save(updatemodel);
            return new CustomerTicketResponse(Constants.MESSAGE_UPDATE_CUSTOMERTICKET_SUCCESS, true);

        }
        return new CustomerTicketResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    private boolean checkData(CustomerTicketDTO model){
        return priceRepository.existsById(model.getIdPrice().getId())
                && customerRepository.existsById(model.getIdCustomer().getId())
                && busSeatRepository.existsById(model.getIdBusSeat().getId())
                && locationRepository.existsById(model.getDepartureLocation().getId())
                && locationRepository.existsById(model.getDestinationLocation().getId())
                && !(model.getDepartureLocation().getId().equals(model.getDestinationLocation().getId()))
                && statusRepository.existsById(model.getIdStatus());
    }
    private String ticketCode(CustomerTicketDTO model){
        PriceModel priceModel = priceRepository.findPriceModelById(model.getIdPrice().getId());
     TimeModel timeModel = timeRepository.findTimeModelById(priceModel.getIdRoutesTime().getIdTime().getId());
        RoutesModel routesModel = routesRepo.findRoutesModelById(priceModel.getIdRoutesTime().getIdRoutes().getId());
        Optional<BusSeat> busSeat = busSeatRepository.findById(model.getIdBusSeat().getId());
        return timeModel.getStartDate().replaceAll("-","") + routesModel.getDeparturePoint() + busSeat.get().getNameSeat();
    }
}
