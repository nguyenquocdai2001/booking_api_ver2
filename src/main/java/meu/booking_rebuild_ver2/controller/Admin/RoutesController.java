package meu.booking_rebuild_ver2.controller.Admin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/routes",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RoutesController {
    @Autowired
    RoutesRepository routesRepo;

    @Autowired
    RoutesTimeRepository routesTimeRepo;


    @PostMapping("/addRoutes")
    public RoutesResponse addRoutes(@RequestBody RoutesModel model){
        log.debug("Inside addRoutes function()", model);
     try {
         model.setCreatedAt(ZonedDateTime.now());
         routesRepo.save(model);
      RoutesResponse response = new RoutesResponse(Constants.MESSAGE_STATUS_ADD_ROUTES_SUCCESS, true , model);
         return response;
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    @GetMapping("/getAllRoutes")
    public RoutesResponse getAllRoutesModels() {
        log.debug("Inside getAllRoutesModels");
        try {
          List<RoutesModel> list = routesRepo.findAll();
            RoutesResponse response = new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/getRoutesById")
    public RoutesResponse getRoutesModelById(@RequestParam UUID id) {

        log.debug("Inside getRoutesModelByID");
        try {
            RoutesModel model = routesRepo.findRoutesModelById(id);
            RoutesResponse response = new RoutesResponse(Constants.MESSAGE_ROUTES_FIND_SUCCESS, true,model);
            return response;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @PutMapping("updateRoutesById")
    public RoutesResponse updateRoutesModelById(@RequestBody RoutesModel routesModel) {
        log.debug("Inside updateRoutesModelById");
        try {
            RoutesModel updateModel = routesRepo.findRoutesModelById(routesModel.getId());
            updateModel.setDeparturePoint(routesModel.getDeparturePoint());
            updateModel.setDestinationPoint(routesModel.getDestinationPoint());
            updateModel.setStatus(routesModel.getStatus());
            updateModel.setCreatedAt(routesModel.getCreatedAt());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(routesModel.getIdUserConfig());
            routesRepo.save(updateModel);
            RoutesResponse response = new RoutesResponse(Constants.MESSAGE_UPDATE_ROUTES_SUCCESS, true,updateModel);
            return response;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/getAllRoutesByStatus")
    public RoutesResponse getAllRoutesModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllRoutesModelsByStatus");
        try {
            List<RoutesModel> list = routesRepo.getRoutesByStatus(statusId);
            RoutesResponse response = new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @DeleteMapping("/deleteRoutesById")
    public RoutesResponse deleteRoutesModel(@RequestParam UUID id) {
        log.debug("Inside deleteRoutesModel");
        try {
            RoutesResponse response;
            if(!routesRepo.existsById(id)){
                response = new RoutesResponse("Invalid ID",true );
            }else if(!routesTimeRepo.getRoutesTimeByRoutes(id).isEmpty()){
                //check if routes_time data contain id
                response = new RoutesResponse("Route Time still has ID, can't delete",true );
            }
            else{
                routesRepo.deleteById(id);
                response = new RoutesResponse("Delete Routes Success",true);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
