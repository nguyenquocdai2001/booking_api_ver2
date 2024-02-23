
package meu.booking_rebuild_ver2.controller.Admin;

import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.TimeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

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
    public TimeResponse addTime(@RequestBody TimeDTO model){
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
            return timeService.getAllTime();
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
            return timeService.findByID(id);
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
    public TimeResponse updateTimeModelById(@RequestBody TimeDTO timeModel) {
        log.debug("Inside updateTimeModelById");
        try {
            return timeService.updateTime(timeModel);
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
            return timeService.getTimeByStatus(statusId);
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



