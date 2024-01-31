package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    // private static  ILoyaltyService loyaltyService;
   // private static  IStatusService statusService;
    @Autowired
    private LoyaltyMapper loyaltyMapper;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private ILoyaltyService loyaltyService;
    public CustomerDTO toDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setIdLoyalty(customer.getLoyalty().getId());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setIdStatus(customer.getStatus().getId());
        customerDTO.setNumberOfTrips(customer.getNumberOfTrips());
        customerDTO.setCreatedAt(customer.getCreatedAt());
        customerDTO.setUpdatedAt(customer.getUpdatedAt());
        customerDTO.setLastUpdated(customer.isLastUpdated());
        return customerDTO;
    }
    public Customer toModel(CustomerDTO customerDTO) throws NotFoundException {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setNumberOfTrips(customerDTO.getNumberOfTrips());
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhone());
        StatusResponse statusModel = statusService.getStatusById(customerDTO.getIdStatus());
        LoyaltyDTO loyaltyModel = loyaltyService.getLoyaltyById(customerDTO.getIdLoyalty());
        customer.setLoyalty(loyaltyMapper.toModel(loyaltyModel));
        customer.setStatus(StatusMapper.toModel(statusModel.getStatus()));
        customer.setLastUpdated(customerDTO.isLastUpdated());
        customer.setUpdatedAt(customerDTO.getUpdatedAt());
        return customer;
    }

}
