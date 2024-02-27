package meu.booking_rebuild_ver2.controller.Passanger;


import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerTicketDTO;
import meu.booking_rebuild_ver2.response.Passanger.CustomerTicketResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ICustomerTicketService;
import meu.booking_rebuild_ver2.service.concretions.Passanger.CustomerTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
@Slf4j
@RestController
@RequestMapping(path = "/customerTicket",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerTicketController {
    @Autowired
    ICustomerTicketService customerTicketService;


    /* addCustomerTicket
     * start
     *  */
    @PostMapping("/addCustomerTicket")
    public CustomerTicketResponse addCustomerTicket(@RequestBody CustomerTicketDTO model){
        log.debug("Inside addCustomerTicket function()");
     try {
         return customerTicketService.createCustomerTicket(model);
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    /* addCustomerTicket
     * end
     *  */



    /* getAllCustomerTicketDTOs
     * start
     *  */
    @GetMapping("/getAllCustomerTicket")
    public CustomerTicketResponse getAllCustomerTicketDTOs() {
        log.debug("Inside getAllCustomerTicketDTOs");
        try {
            return customerTicketService.getAllCustomerTicket();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllCustomerTicketDTOs
     * end
     *  */

    /* getCustomerTicketDTOById
     * start
     *  */
    @GetMapping("/getCustomerTicketById")
    public CustomerTicketResponse getCustomerTicketDTOById(@RequestParam UUID id) {
        log.debug("Inside getCustomerTicketDTOByID");
        try {
            return customerTicketService.findByID(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getCustomerTicketDTOById
     * end
     *  */

    /* updateCustomerTicketDTOById
     * start
     *  */
    @PutMapping("/updateCustomerTicketById")
    public CustomerTicketResponse updateCustomerTicketDTOById(@RequestBody CustomerTicketDTO customerTicketDTO) {
        log.debug("Inside updateCustomerTicketDTOById");
        try {
            return customerTicketService.updateCustomerTicket(customerTicketDTO);

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* updateCustomerTicketDTOById
     * end
     *  */


    /* getAllCustomerTicketDTOsByStatus
     * start
     *  */
    @GetMapping("/getAllCustomerTicketByStatus")
    public CustomerTicketResponse getAllCustomerTicketDTOsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllCustomerTicketDTOsByStatus");
        try {
            return customerTicketService.getCustomerTicketByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllCustomerTicketDTOsByStatus
     * end
     *  */

//    get routes time by time start
    @GetMapping("/getCustomerTicketByTime")
    public CustomerTicketResponse getCustomerTicketByTime(@RequestParam UUID timeId){
        log.debug("Inside getCustomerTicketByTime");
        try{
            return customerTicketService.getCustomerTicketByTime(timeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //    get routes time by time end

    //    get routes time by routes start
    @GetMapping("/getCustomerTicketByRoutes")
    public CustomerTicketResponse getCustomerTicketByRoutes(@RequestParam UUID routesId){
        log.debug("Inside getCustomerTicketByRoutes");
        try{
            return customerTicketService.getCustomerTicketByRoutes(routesId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by routes end


    /*
     * deleteByid
     *
     * */
    @DeleteMapping("/deleteCustomerTicketById")
    public CustomerTicketResponse deleteCustomerTicketDTO(@RequestParam UUID id) {
        log.debug("Inside deleteCustomerTicketDTO");
        try {
            return customerTicketService.deleteById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //    get routes time by routes time start
    @GetMapping("/getCustomerTicketByRoutesTime")
    public CustomerTicketResponse getCustomerTicketByRoutesTime(@RequestParam UUID routesTimeId){
        log.debug("Inside getCustomerTicketByRoutesTime");
        try{
            return customerTicketService.getCustomerTicketByRoutesTime(routesTimeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by routes time end

    //    get routes time by ticket code start
    @GetMapping("/getCustomerTicketByTicketCode")
    public CustomerTicketResponse getCustomerTicketByTicketCode(@RequestParam String ticketCode){
        log.debug("Inside getCustomerTicketByTicketCode");
        try{
            return customerTicketService.getCustomerTicketByTicketCode(ticketCode);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by ticket code end

    //    get routes time by bus seat start
    @GetMapping("/getCustomerTicketByBusSeat")
    public CustomerTicketResponse getCustomerTicketByBusSeat(@RequestParam UUID busSeatId){
        log.debug("Inside getCustomerTicketByBusSeat");
        try{
            return customerTicketService.getCustomerTicketByBusSeat(busSeatId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by bus seat end

    //    get routes time by departure start
    @GetMapping("/getCustomerTicketByDeparture")
    public CustomerTicketResponse getCustomerTicketByDeparture(@RequestParam UUID departureLocationId){
        log.debug("Inside getCustomerTicketByDeparture");
        try{
            return customerTicketService.getCustomerTicketByDeparture(departureLocationId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by departure end
    //    get routes time by departure start
    @GetMapping("/getCustomerTicketByDestination")
    public CustomerTicketResponse getCustomerTicketByDestination(@RequestParam UUID destinationLocationId){
        log.debug("Inside getCustomerTicketByDestination");
        try{
            return customerTicketService.getCustomerTicketByDestination(destinationLocationId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by departure end
    @PutMapping("/payTicket")
    public CustomerTicketResponse payTicket(@RequestParam UUID id){
        log.debug("Inside payTicket");
        try{
            return customerTicketService.setPayForTicket(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    //     get routes time by departure end
}
