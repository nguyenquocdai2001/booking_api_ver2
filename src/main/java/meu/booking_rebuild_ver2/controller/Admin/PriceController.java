
package meu.booking_rebuild_ver2.controller.Admin;

import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.response.Admin.PriceResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-10
 * */
@Slf4j
@RestController
@RequestMapping(path = "/price",  produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {
    @Autowired
    PriceService priceService;

   /* addNewPrice
   * start
   *  */
    @PostMapping("/addPrice")
    public PriceResponse addPrice(@RequestBody PriceModel model){
        log.debug("Inside addPrice function()");
     try {
         return priceService.createPrice(model);
     }catch(Exception e){
         throw new BadRequestException(e.getMessage());
     }
    }
    /* addNewPrice
    * end
    * */


    /* getAllPrice
     * start
     *  */
    @GetMapping("/getAllPrice")
    public PriceResponse getAllPriceModels() {
        log.debug("Inside getAllPriceModels");
        try {
            return priceService.getAllPrice();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPrice
     * end
     *  */


    /* getPriceById
     * start
     *  */
    @GetMapping("/getPriceById")
    public PriceResponse getPriceModelById(@RequestParam UUID id) {
        log.debug("Inside getPriceModelByID");
        try {
            return priceService.findByID(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getPriceById
     * end
     *  */


    /* updatePriceById
     * start
     *  */
    @PutMapping("/updatePrice")
    public PriceResponse updatePriceModelById(@RequestBody PriceModel priceModel) {
        log.debug("Inside updatePriceModelById");
        try {
            return priceService.updatePrice(priceModel);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* updatePriceById
     * end
     *  */

    /* getAllPriceByStatusID
     * start
     *  */
    @GetMapping("/getByStatus")
    public PriceResponse getAllPriceModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllPriceModelsByStatus");
        try {
            return priceService.getPriceByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPriceByStatusID
     * end
     *  */

    /*
    * deleteByid
    *
    * */
    @DeleteMapping("deletePriceById")
    public PriceResponse deletePriceModel(@RequestParam UUID id) {
        log.debug("Inside deletePriceModel");
        try {
            return priceService.deleteById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* getAllPriceByBusTypeID
     * start
     *  */
    @GetMapping("/getByBusType")
    public PriceResponse getAllPriceModelsByBusType(@RequestParam UUID busTypeId) {
        log.debug("Inside getAllPriceModelsByStatus");
        try {
            return priceService.getPriceByBusType(busTypeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPriceByBusTypeID
     * end
     *  */

    /* getAllPriceModelsByRoutesTimeID
     * start
     *  */
    @GetMapping("/getByRoutesTime")
    public PriceResponse getAllPriceModelsByRoutesTime(@RequestParam UUID routesTimeId) {
        log.debug("Inside getAllPriceModelsByStatus");
        try {
            return priceService.getPriceByRoutesTime(routesTimeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* getAllPriceModelsByRoutesTimeID
     * end
     *  */
}



