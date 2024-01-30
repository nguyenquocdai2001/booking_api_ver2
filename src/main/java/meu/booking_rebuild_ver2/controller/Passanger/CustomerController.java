package meu.booking_rebuild_ver2.controller.Passanger;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/*
 * author: Nguyen Minh Tam
 * ticket: BS-3
 * */
@RestController
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private UserID userID;
    @PostMapping("addCustomer")
    public GenericResponse addNewCustomer(@RequestBody @Valid CustomerRequest request, @NotNull HttpSession httpSession) throws GenericResponseExceptionHandler {
        userID.setUserValue((UUID) httpSession.getAttribute("USER_ID"));
        return customerService.addCustomer(request);
    }
    @GetMapping("getCustomer")
    public CustomerResponse getCustomer(@RequestParam UUID id) throws NotFoundException {
        return customerService.getCustomerById(id);
    }
    @GetMapping(path = "getCustomerByLoyaltyId")
    public Page<CustomerResponse> getCustomerByLoyaltyId(@RequestParam UUID id, @RequestParam(defaultValue = "0") Integer page) throws NotFoundException {
        return  customerService.getCustomerByLoyaltyWithPage(id, page);
    }
    @GetMapping(path = "getCustomerByPhone")
    public Page<CustomerResponse> getCustomerByPhone(@RequestParam String phone, @RequestParam(defaultValue = "0") Integer page) throws NotFoundException {
        return  customerService.getCustomerByPhoneWithPage(phone, page);
    }

    @PutMapping("updateCustomer")
    public GenericResponse updateCustomer(@RequestParam UUID id, @RequestBody UpdateCustomerRequest request, @NotNull HttpSession httpSession) throws NotFoundException, GenericResponseExceptionHandler{
        userID.setUserValue((UUID) httpSession.getAttribute("USER_ID"));
        return customerService.updateCustomer(id, request);
    }
    @PutMapping("updateCustomerByLoyalty")
    public GenericResponse updateCustomerByLoyalty(@RequestParam UUID id, @RequestBody UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler{
        return customerService.updateCustomerByLoyalty(id, request);
    }
    @DeleteMapping("deleteCustomerById")
    public GenericResponse deleteCustomer(@RequestParam UUID id) throws NotFoundException{
        return customerService.deleteCustomerById(id);
    }
}
