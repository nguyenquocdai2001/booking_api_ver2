
package meu.booking_rebuild_ver2.controller.Admin;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-13
 * */
@Slf4j
@RestController
@RequestMapping(path = "/time",  produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeController {
    @Autowired
    TimeRepository timeRepo;
    @Autowired
    RoutesTimeRepository routesTimeRepo;


   /* addNewTime
   * start
   *  */
    @PostMapping("/addTime")
    public TimeResponse addTime(@RequestBody TimeModel model){
        log.debug("Inside addTime function()", model);
     try {
        /* checkTimeInput start*/
         TimeResponse response;
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate currentDate = LocalDate.now();
         LocalDate specificDateStart = LocalDate.parse(model.getStartDate(),formatter);
         LocalDate specificDateEnd = LocalDate.parse(model.getEndDate(),formatter);
         if(currentDate.isAfter(specificDateStart) || currentDate.isAfter(specificDateEnd) || specificDateStart.isAfter(specificDateEnd)){
             response  = new TimeResponse("The invalid day",false, model);
         }
         /* checkTimeInput end*/
         else {
             model.setCreatedAt(ZonedDateTime.now());
             timeRepo.save(model);
             response = new TimeResponse(Constants.MESSAGE_STATUS_ADD_TIME_SUCCESS, true, model);
         }
         return response;
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    /* addNewTime
    * end
    * */


    /* getAllTime
     * start
     *  */
    @GetMapping("/getAllTime")
    public TimeResponse getAllTimeModels() {
        log.debug("Inside getAllTimeModels");
        try {
          List<TimeModel> list = timeRepo.findAll();
            TimeResponse response = new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllTime
     * end
     *  */


    /* getTimeById
     * start
     *  */
    @GetMapping("/getTimeById")
    public TimeResponse getTimeModelById(@RequestParam UUID id) {

        log.debug("Inside getTimeModelByID");
        try {
            TimeModel model = timeRepo.findTimeModelById(id);
            TimeResponse response = new TimeResponse(Constants.MESSAGE_TIME_FIND_SUCCESS, true,model);
            return response;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getTimeById
     * end
     *  */


    /* updateTimeById
     * start
     *  */
    @PutMapping("/updateTime")
    public TimeResponse updateTimeModelById(@RequestBody TimeModel timeModel) {
        log.debug("Inside updateTimeModelById");
        try {
            TimeModel updateModel = timeRepo.findTimeModelById(timeModel.getId());
            updateModel.setStartTime(timeModel.getStartTime());
            updateModel.setEndTime(timeModel.getEndTime());
            updateModel.setStartDate(timeModel.getStartDate());
            updateModel.setEndDate(timeModel.getEndDate());
            updateModel.setStatus(timeModel.getStatus());
            updateModel.setCreatedAt(timeModel.getCreatedAt());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(timeModel.getIdUserConfig());
            timeRepo.save(updateModel);
            /*thiếu userConfig*/
            TimeResponse response = new TimeResponse(Constants.MESSAGE_UPDATE_TIME_SUCCESS, true,updateModel);
            return response;

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* updateTimeById
     * end
     *  */

    /* getAllTimeByStatusID
     * start
     *  */
    @GetMapping("/getAllTimeByStatus")
    public TimeResponse getAllTimeModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllTimeModelsByStatus");
        try {
            List<TimeModel> list = timeRepo.getTimeByStatus(statusId);
            TimeResponse response = new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllTimeByStatusID
     * end
     *  */

    /*
    * deleteByid
    *
    * */
    @DeleteMapping("deleteTimeById")
    public TimeResponse deleteTimeModel(@RequestParam UUID id) {
        log.debug("Inside deleteTimeModel");
        try {
            TimeResponse response;
            if(!timeRepo.existsById(id)){
                response = new TimeResponse("Invalid ID",true );
            }else if(!routesTimeRepo.getRoutesTimeByTime(id).isEmpty()){
                //check if routes_time data contain id
                response = new TimeResponse("Route Time still has ID, can't delete",true );
            }
            else{
                timeRepo.deleteById(id);
                response = new TimeResponse("Delete Time Success",true );
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}



