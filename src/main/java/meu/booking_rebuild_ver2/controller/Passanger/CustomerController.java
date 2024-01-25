package meu.booking_rebuild_ver2.controller.Passanger;

import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDto;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import meu.booking_rebuild_ver2.request.Passanger.UpdateCustomerRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.List;
/*
 * author: Nguyen Minh Tam
 * ticket: BS-3
 * */
@RestController
@RequestMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @PostMapping("addCustomer")
    public GenericResponse addNewCustomer(@RequestBody @Valid CustomerRequest request) throws GenericResponseExceptionHandler {
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
    public GenericResponse updateCustomer(@RequestParam UUID id, @RequestBody UpdateCustomerRequest request) throws NotFoundException, GenericResponseExceptionHandler{
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
