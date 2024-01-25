package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import org.springframework.validation.BindingResult;

import java.util.UUID;

public interface ICustomerService {
    GenericResponse addCustomer(CustomerRequest request) throws GenericResponseExceptionHandler;
    CustomerResponse getCustomerById(UUID id) throws NotFoundException;
    GenericResponse updateCustomer(UUID id,UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler;
}
