package meu.booking_rebuild_ver2.controller.Admin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.response.Admin.RoutesResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-15
 * */
@Slf4j
@RestController
@RequestMapping(path = "/routes",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RoutesController {
    @Autowired
    IRoutesService routesService;

    @PostMapping("/addRoutes")
    public RoutesResponse addRoutes(@RequestBody RoutesModel model){
        log.debug("Inside addRoutes function()");
     try {
         return routesService.createRoutes(model);
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    @GetMapping("/getAllRoutes")
    public RoutesResponse getAllRoutesModels() {
        log.debug("Inside getAllRoutesModels");
        try {
            return  routesService.getAllRoutes();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/getRoutesById")
    public RoutesResponse getRoutesModelById(@RequestParam UUID id) {

        log.debug("Inside getRoutesModelByID");
        try {
           return routesService.findByID(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @PutMapping("updateRoutesById")
    public RoutesResponse updateRoutesModelById(@RequestBody RoutesModel routesModel) {
        log.debug("Inside updateRoutesModelById");
        try {
            if(routesModel.getId() != null) {
                return routesService.updateRoutes(routesModel);
            }
            return new RoutesResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/getAllRoutesByStatus")
    public RoutesResponse getAllRoutesModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllRoutesModelsByStatus");
        try {
            return routesService.getRoutesByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/deleteRoutesById")
    public RoutesResponse deleteRoutesModel(@RequestParam UUID id) {
        log.debug("Inside deleteRoutesModel");
        try {
            return routesService.deleteById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
