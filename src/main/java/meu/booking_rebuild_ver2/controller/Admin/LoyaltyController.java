package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Minh Tam
 * ticket: BS-2
 * */
@RestController
@RequestMapping(path = "/loyalty", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoyaltyController {
    @Autowired
    private ILoyaltyService loyaltyService;
    /*
    * The function to add new loyalty with request with no duplicate rank and discount.
    * The model will ensure that the rank and discount are unique
     */
    @PostMapping(path = "addLoyalty")
    public GenericResponse addNewLoyalty(@RequestBody @Valid Loyalty request){
        return loyaltyService.addNewLoyalty(request);
    }
    /*
     * The function to get all rank and it's discount in table loyalty.
     */
    @GetMapping(path = "getAllLoyalty")
    public Iterable<Loyalty> getAllLoyalty(){
        return  loyaltyService.getAllLoyalty();
    }
    @GetMapping(path = "getLoyaltyByRank")
    public Optional<Loyalty> getLoyalty(@RequestBody LoyaltyRequest request){
        return  loyaltyService.getLoyaltyByRank(request.getRank());
    }
    @PutMapping(path = "updateLoyalty")
    public GenericResponse updateLoyalty(@RequestParam UUID id, @RequestBody LoyaltyRequest request){
        return loyaltyService.updateLoyalty(id, request);
    }
    @DeleteMapping(path = "deleteLoyalty")
    public GenericResponse deleteLoyalty(@RequestParam UUID id){
        return loyaltyService.deleteLoyalty(id);
    }
}
