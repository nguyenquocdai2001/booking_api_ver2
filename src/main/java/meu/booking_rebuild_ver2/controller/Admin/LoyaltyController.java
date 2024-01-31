package meu.booking_rebuild_ver2.controller.Admin;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Nguyen Minh Tam
 * ticket: BS-2
 * */
@RestController
@RequestMapping(path = "/loyalty", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoyaltyController {
    @Autowired
    private ILoyaltyService loyaltyService;
    @Autowired
    private ICustomerService customerService;
    /*
    * The function to add new loyalty with request with no duplicate rank and discount.
    * The model will ensure that the rank and discount are unique
     */
    @PostMapping(path = "addLoyalty")
    public GenericResponse addNewLoyalty(@RequestBody @Valid Loyalty request) throws GenericResponseExceptionHandler {
        return loyaltyService.addNewLoyalty(request);
    }
    /*
     * The function to get all rank and it's discount in table loyalty.
     */
    @GetMapping(path = "getAllLoyalty")
    public Iterable<LoyaltyDTO> getAllLoyalty(){
        return  loyaltyService.getAllLoyalty();
    }
    @GetMapping(path = "getLoyaltyByRank")
    /*
     * The function to get the detail of rank. With the rank is input from user
     */
    public Optional<Loyalty> getLoyalty(@RequestBody LoyaltyRequest request) throws NotFoundException, GenericResponseExceptionHandler {
        return  loyaltyService.getLoyaltyByRank(request.getRank());
    }
    /*
     * The function used to get the rank with price will be the norm according to loyalty_spent
     */
    @GetMapping(path = "getLoyaltyByPrice")
    public LoyaltyDTO getLoyaltyByPrice(@RequestParam double price) throws GenericResponseExceptionHandler{
        return loyaltyService.getLoyaltyByPrice(price);
    }
    /*
     * The function used to get the rank with price will be the norm according to id loyalty
     */
    @GetMapping(path = "getLoyaltyById")
    public LoyaltyDTO getLoyaltyById(@RequestParam UUID id) throws GenericResponseExceptionHandler, NotFoundException {
        return loyaltyService.getLoyaltyById(id);
    }
    /*
     * The function to update the loyalty from id and dto
     */
    @PutMapping(path = "updateLoyalty")
    public GenericResponse updateLoyalty(@RequestParam UUID id, @RequestBody LoyaltyRequest request, @NotNull HttpSession httpSession) throws NotFoundException, GenericResponseExceptionHandler {
        return loyaltyService.updateLoyalty(id, request, httpSession);
    }
    /*
     * The function to delete the loyalty form id
     */
    @DeleteMapping(path = "deleteLoyalty")
    public GenericResponse deleteLoyalty(@RequestParam UUID id) throws NotFoundException, GenericResponseExceptionHandler {
        System.out.println(customerService.getCustomerByLoyalty(id).getListCustomer());
         customerService.handleUpdateCustomerWhenLoyaltyDelete(customerService.getCustomerByLoyalty(id).getListCustomer());
        return loyaltyService.deleteLoyalty(id);
    }
}
