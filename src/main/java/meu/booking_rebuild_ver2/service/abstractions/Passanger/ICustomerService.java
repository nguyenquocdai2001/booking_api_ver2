package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.exception.GenericResponseException;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.request.LoginRequest;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;
import java.util.List;
public interface ICustomerService {
    GenericResponse addCustomer(CustomerRequest request) throws GenericResponseException;
    CustomerResponse getCustomerById(UUID id) throws NotFoundException;
    CustomerResponse getCustomersByPhone(String phone) throws NotFoundException;
    CustomerResponse getCustomerByLoyalty(UUID idLoyalty) throws NotFoundException;
    CustomerResponse getCustomerByPhone(String phone);
    GenericResponse updateCustomer(UUID id,UpdateCustomerRequest request) throws NotFoundException, GenericResponseException;
    GenericResponse updateCustomerByLoyalty(UUID id, UpdateCustomerRequest request) throws NotFoundException, GenericResponseException;
    Page<CustomerResponse> getCustomerByPhoneWithPage(String phone, Integer page) throws NotFoundException;
    Page<CustomerResponse> getCustomerByLoyaltyWithPage(UUID IdLoyalty, Integer page) throws NotFoundException;
    void handleUpdateCustomerWhenLoyaltyDelete(List<CustomerDTO> customerDTOS) throws GenericResponseException, NotFoundException;
    GenericResponse deleteCustomerById(UUID id) throws NotFoundException;
    LoginResponse customerLoginHandle(String phone, String password) throws NotFoundException, GenericResponseException;
    UserDetails loadCustomerByPhone(String phone);

}
