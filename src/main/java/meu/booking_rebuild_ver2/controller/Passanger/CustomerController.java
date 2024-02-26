package meu.booking_rebuild_ver2.controller.Passanger;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.exception.GenericResponseException;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.request.LoginRequest;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    /*
     * The endpoint to add new customer
     *
     * */
    @PostMapping("addCustomer")
    public GenericResponse addNewCustomer(@RequestBody @Valid CustomerRequest request) throws GenericResponseException {
//        userID.setUserValue((UUID) httpSession.getAttribute("USER_ID"));
        return customerService.addCustomer(request);
    }
    /*
     * The endpoint to get  customer by ID customer
     *
     * */
    @GetMapping("getCustomer")
    public CustomerResponse getCustomer(@RequestParam UUID id) throws NotFoundException {
        return customerService.getCustomerById(id);
    }
    /*
     * The endpoint to get  customers by ID loyalty
     *
     * */
    @GetMapping(path = "getCustomerByLoyaltyId")
    public Page<CustomerResponse> getCustomerByLoyaltyId(@RequestParam UUID id, @RequestParam(defaultValue = "0") Integer page) throws NotFoundException {
        return  customerService.getCustomerByLoyaltyWithPage(id, page);
    }
    /*
     * The endpoint to get  customers by phone customer
     *
     * */
    @GetMapping(path = "searchCustomerByPhone")
    public Page<CustomerResponse> getCustomerByPhone(@RequestParam String phone, @RequestParam(defaultValue = "0") Integer page) throws NotFoundException {
        return  customerService.getCustomerByPhoneWithPage(phone, page);
    }
    /*
     * The endpoint to update cuustomer with total paid
     *
     * */
    @PutMapping("updateCustomer")
    public GenericResponse updateCustomer(@RequestParam UUID id, @RequestBody UpdateCustomerRequest request, @NotNull HttpSession httpSession) throws NotFoundException, GenericResponseException {
        userID.setUserValue((UUID) httpSession.getAttribute("USER_ID"));
        return customerService.updateCustomer(id, request);
    }
    /*
     * The endpoint to update cuustomer with loyalty id or loyalty rank
     *
     * */
    @PutMapping("updateCustomerByLoyalty")
    public GenericResponse updateCustomerByLoyalty(@RequestParam UUID id, @RequestBody UpdateCustomerRequest request) throws NotFoundException, GenericResponseException {
        return customerService.updateCustomerByLoyalty(id, request);
    }
    /*
     * The endpoint to delete cuustomer with id
     *
     * */

    @PostMapping("loginCustomer")
    public LoginResponse loginCustomer(@RequestBody LoginRequest request,HttpSession session) throws NotFoundException, GenericResponseException{
        String phone = request.getPhone();
        String password = request.getPassword();
        return customerService.customerLoginHandle(phone, password);
    }
    @DeleteMapping("deleteCustomerById")
    public GenericResponse deleteCustomer(@RequestParam UUID id) throws NotFoundException{
        return customerService.deleteCustomerById(id);
    }
    @GetMapping("getCustomerByPhone")
    public CustomerResponse getCustomerByPhone(@RequestParam String phone){
        return customerService.getCustomerByPhone(phone);
    }

}
