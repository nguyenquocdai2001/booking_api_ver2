package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.List;
public interface ICustomerService {
    GenericResponse addCustomer(CustomerRequest request) throws GenericResponseExceptionHandler;
    CustomerResponse getCustomerById(UUID id) throws NotFoundException;
    CustomerResponse getCustomerByPhone(String phone) throws NotFoundException;
    CustomerResponse getCustomerByLoyalty(UUID idLoyalty) throws NotFoundException;
    GenericResponse updateCustomer(UUID id,UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    Page<CustomerResponse> getCustomerByPhoneWithPage(String phone, Integer page) throws NotFoundException;
    Page<CustomerResponse> getCustomerByLoyaltyWithPage(UUID IdLoyalty, Integer page) throws NotFoundException;
    void handleUpdateCustomerWhenLoyaltyDelete(List<CustomerDTO> customerDTOS) throws GenericResponseExceptionHandler, NotFoundException;
    GenericResponse deleteCustomerById(UUID id) throws NotFoundException;
}
