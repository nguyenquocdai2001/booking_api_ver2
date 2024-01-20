
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
import meu.booking_rebuild_ver2.service.concretions.TimeService;
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
    TimeService timeService;

   /* addNewTime
   * start
   *  */
    @PostMapping("/addTime")
    public TimeResponse addTime(@RequestBody TimeModel model){
        log.debug("Inside addTime function()");
     try {
         return timeService.createTime(model);
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
            List<TimeModel> list = timeService.getAllTime();
            return new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
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
            TimeResponse response;
                TimeModel model = timeService.findByID(id);
                if(model!= null) {
                    response = new TimeResponse(Constants.MESSAGE_TIME_FIND_SUCCESS, true, model);
                    return response;
                }
            response = new TimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
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
            TimeResponse response;
            if(timeModel.getId() != null) {
                 response = timeService.updateTime(timeModel);
                return response;
            }
            response = new TimeResponse("Something went wrong", false);
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
            List<TimeModel> list = timeService.getTimeByStatus(statusId);
            TimeResponse response;
            if(!list.isEmpty()){
                response = new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
                return response;
            }
            return new TimeResponse("Status not found", false);
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
            return timeService.deleteById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}



