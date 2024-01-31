package meu.booking_rebuild_ver2.controller.Admin;


import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesTimeDTO;
import meu.booking_rebuild_ver2.response.Admin.RoutesTimeResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.RoutesTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;
/*
 * author: Quoc Dat
 * ticket: BS-14
 * */
@Slf4j
@RestController
@RequestMapping(path = "/routeTime",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RoutesTimeController {
    @Autowired
    RoutesTimeService routesTimeService;


    /* addRoutesTime
     * start
     *  */
    @PostMapping("/addRoutesTime")
    public RoutesTimeResponse addRoutesTime(@RequestBody RoutesTimeDTO model){
        log.debug("Inside addRoutesTime function()");
     try {
         return routesTimeService.createRoutesTime(model);
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    /* addRoutesTime
     * end
     *  */



    /* getAllRoutesTimeDTOs
     * start
     *  */
    @GetMapping("/getAllRoutesTime")
    public RoutesTimeResponse getAllRoutesTimeDTOs() {
        log.debug("Inside getAllRoutesTimeDTOs");
        try {
            return routesTimeService.getAllRoutesTime();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllRoutesTimeDTOs
     * end
     *  */

    /* getRoutesTimeDTOById
     * start
     *  */
    @GetMapping("/getRoutesTimeById")
    public RoutesTimeResponse getRoutesTimeDTOById(@RequestParam UUID id) {
        log.debug("Inside getRoutesTimeDTOByID");
        try {
            return routesTimeService.findByID(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getRoutesTimeDTOById
     * end
     *  */

    /* updateRoutesTimeDTOById
     * start
     *  */
    @PutMapping("/updateRoutesTimeById")
    public RoutesTimeResponse updateRoutesTimeDTOById(@RequestBody RoutesTimeDTO routesTimeDTO) {
        log.debug("Inside updateRoutesTimeDTOById");
        try {
            return routesTimeService.updateRoutesTime(routesTimeDTO);

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* updateRoutesTimeDTOById
     * end
     *  */


    /* getAllRoutesTimeDTOsByStatus
     * start
     *  */
    @GetMapping("/getAllRoutesTimeByStatus")
    public RoutesTimeResponse getAllRoutesTimeDTOsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllRoutesTimeDTOsByStatus");
        try {
            return routesTimeService.getRoutesTimeByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllRoutesTimeDTOsByStatus
     * end
     *  */

//    get routes time by time start
    @GetMapping("/getRoutesTimeByTime")
    public RoutesTimeResponse getRoutesTimeByTime(@RequestParam UUID timeId){
        log.debug("Inside getRoutesTimeByTime");
        try{
            return routesTimeService.getRoutesTimeByTime(timeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //    get routes time by time end

    //    get routes time by routes start
    @GetMapping("/getRoutesTimeByRoutes")
    public RoutesTimeResponse getRoutesTimeByRoutes(@RequestParam UUID routesId){
        log.debug("Inside getRoutesTimeByRoutes");
        try{
            return routesTimeService.getRoutesTimeByRoutes(routesId);
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
    public RoutesTimeResponse deleteRoutesTimeDTO(@RequestParam UUID id) {
        log.debug("Inside deleteRoutesTimeDTO");
        try {
            return routesTimeService.deleteById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
