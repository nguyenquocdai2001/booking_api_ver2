package meu.booking_rebuild_ver2.controller.Admin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
/*
 * author: Quoc Dat
 * ticket: BS-13
 * */
@Slf4j
@RestController
@RequestMapping(path = "/routeTime",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RoutesTimeController {
    @Autowired
    RoutesTimeRepository routesTimeRepo;
    @Autowired
    RoutesRepository routesRepo;
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    StatusRepository statusRepo;

    /* addRoutesTime
     * start
     *  */
    @PostMapping("/addRoutesTime")
    public RoutesTimeResponse addRoutesTime(@RequestBody RoutesTimeModel model){
        log.debug("Inside addRoutesTime function()");
     try {
         RoutesTimeResponse response;
         //check ID_Time & ID_routes
         if(!checkIdTimeAndRoute(model)){
             response = new RoutesTimeResponse("Time or Route or Status is Invalid", false , model);
         }else {
             model.setCreatedAt(ZonedDateTime.now());
             routesTimeRepo.save(model);
             response = new RoutesTimeResponse(Constants.MESSAGE_STATUS_ADD_ROUTE_TIME_SUCCESS, true, model);
         }
         return response;
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    /* addRoutesTime
     * end
     *  */

    //check ID_Time & ID_routes start
    private boolean checkIdTimeAndRoute(RoutesTimeModel model){
        return timeRepository.existsById(model.getIdTime().getId())
                && routesRepo.existsById(model.getIdRoutes().getId())
                && statusRepo.existsById(model.getStatus().getId());
    }
    //check ID_Time & ID_routes end

    /* getAllRoutesTimeModels
     * start
     *  */
    @GetMapping("/getAllRoutesTime")
    public RoutesTimeResponse getAllRoutesTimeModels() {
        log.debug("Inside getAllRoutesTimeModels");
        try {
          List<RoutesTimeModel> list = routesTimeRepo.findAll();
            return new RoutesTimeResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_TIME_SUCCESS, true, list);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllRoutesTimeModels
     * end
     *  */

    /* getRoutesTimeModelById
     * start
     *  */
    @GetMapping("/getRoutesTimeById")
    public RoutesTimeResponse getRoutesTimeModelById(@RequestParam UUID id) {

        log.debug("Inside getRoutesTimeModelByID");
        try {
            RoutesTimeModel model = routesTimeRepo.findRoutesTimeModelById(id);
            return new RoutesTimeResponse(Constants.MESSAGE_ROUTES_TIME_FIND_SUCCESS, true,model);

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getRoutesTimeModelById
     * end
     *  */

    /* updateRoutesTimeModelById
     * start
     *  */
    @PutMapping("/updateRoutesTimeById")
    public RoutesTimeResponse updateRoutesTimeModelById(@RequestBody RoutesTimeModel routesTimeModel) {
        log.debug("Inside updateRoutesTimeModelById");
        try {
            RoutesTimeResponse response;
            RoutesTimeModel updateModel = routesTimeRepo.findRoutesTimeModelById(routesTimeModel.getId());

            if(!checkIdTimeAndRoute(routesTimeModel)){
                response = new RoutesTimeResponse("Time or Route or Status is Invalid", false , routesTimeModel);
            }else {
                updateModel.setIdRoutes(routesTimeModel.getIdRoutes());
                updateModel.setIdTime(routesTimeModel.getIdTime());
                updateModel.setStatus(routesTimeModel.getStatus());
                updateModel.setCreatedAt(routesTimeModel.getCreatedAt());
                updateModel.setUpdatedAt(ZonedDateTime.now());
                updateModel.setIdUserConfig(routesTimeModel.getIdUserConfig());
                routesTimeRepo.save(updateModel);
                /*thiếu userConfig*/
                response = new RoutesTimeResponse(Constants.MESSAGE_UPDATE_ROUTES_TIME_SUCCESS, true, updateModel);
            }
            return response;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* updateRoutesTimeModelById
     * end
     *  */


    /* getAllRoutesTimeModelsByStatus
     * start
     *  */
    @GetMapping("/getAllRoutesTimeByStatus")
    public RoutesTimeResponse getAllRoutesTimeModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllRoutesTimeModelsByStatus");
        try {
            List<RoutesTimeModel> list = routesTimeRepo.getRoutesTimeByStatus(statusId);
            return new RoutesTimeResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_TIME_SUCCESS, true, list);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllRoutesTimeModelsByStatus
     * end
     *  */

//    get routes time by time start
    @GetMapping("/getRoutesTimeByTime")
    public RoutesTimeResponse getRoutesTimeByTime(@RequestParam UUID timeId){
        log.debug("Inside getRoutesTimeByTime");
        try{
            RoutesTimeResponse response;
            if(!timeRepository.existsById(timeId)){
                response = new RoutesTimeResponse("Invalid ID", true);
            }else{
                List<RoutesTimeModel> routesTimeModelList = routesTimeRepo.getRoutesTimeByTime(timeId);
                response = new RoutesTimeResponse("Get routes by time success",true ,routesTimeModelList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //    get routes time by time end

    //    get routes time by routes start
    @GetMapping("/getRoutesTimeByRoutes")
    public RoutesTimeResponse getRoutesTimeByRoutes(@RequestParam UUID timeId){
        log.debug("Inside getRoutesTimeByTime");
        try{
            RoutesTimeResponse response;
            if(!routesRepo.existsById(timeId)){
                response = new RoutesTimeResponse("Invalid ID", true);
            }else{
                List<RoutesTimeModel> routesTimeModelList = routesTimeRepo.getRoutesTimeByRoutes(timeId);
                response = new RoutesTimeResponse("Get routes by routes success",true ,routesTimeModelList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by routes end


    /*
     * deleteByid
     *
     * */
    @DeleteMapping("/deleteRoutesTimeById")
    public RoutesTimeResponse deleteRoutesTimeModel(@RequestParam UUID id) {
        log.debug("Inside deleteRoutesTimeModel");
        try {
            RoutesTimeResponse response;
            if(routesTimeRepo.existsById(id)){
                routesTimeRepo.deleteById(id);
                response = new RoutesTimeResponse("Delete Time Success",true );
            }else {
                response = new RoutesTimeResponse("Invalid ID",false );
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
