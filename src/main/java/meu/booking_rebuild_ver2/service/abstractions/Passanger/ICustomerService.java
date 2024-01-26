package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
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
    List<CustomerResponse> getCustomerByPhone(String phone) throws NotFoundException;
    List<CustomerResponse> getCustomerByLoyalty(UUID idLoyalty) throws NotFoundException;
    GenericResponse updateCustomer(UUID id,UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    Page<CustomerResponse> getCustomerByPhoneWithPage(String phone, Integer page);
    Page<CustomerResponse> getCustomerByLoyaltyWithPage(UUID IdLoyalty, Integer page);
    GenericResponse deleteCustomerById(UUID id) throws NotFoundException;
}
