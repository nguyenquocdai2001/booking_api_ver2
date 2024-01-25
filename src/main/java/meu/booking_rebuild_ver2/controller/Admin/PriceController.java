
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
            List<PriceModel> list = priceService.getAllPrice();
            return new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
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
            PriceResponse response;
                PriceModel model = priceService.findByID(id);
                if(model!= null) {
                    response = new PriceResponse(Constants.MESSAGE_PRICE_FIND_SUCCESS, true, model);
                    return response;
                }
            response = new PriceResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
            return response;
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
            PriceResponse response;
            if(priceModel.getId() != null) {
                 response = priceService.updatePrice(priceModel);
                return response;
            }
            response = new PriceResponse("Something went wrong", false);
            return response;
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
    @GetMapping("/getAllPriceByStatus")
    public PriceResponse getAllPriceModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllPriceModelsByStatus");
        try {
            List<PriceModel> list = priceService.getPriceByStatus(statusId);
            PriceResponse response;
            if(!list.isEmpty()){
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            return new PriceResponse("Status not found", false);
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
//    @GetMapping("/getAllPriceByStatus")
//    public PriceResponse getAllPriceModelsByBusType(@RequestParam UUID busTypeId) {
//        log.debug("Inside getAllPriceModelsByStatus");
//        try {
//            List<PriceModel> list = priceService.getPriceByBusType(busTypeId);
//            PriceResponse response;
//            if(!list.isEmpty()){
//                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
//                return response;
//            }
//            return new PriceResponse("BusType not found", false);
//        }catch (Exception e){
//            throw new BadRequestException(e.getMessage());
//        }
//    }
    /* getAllPriceByBusTypeID
     * end
     *  */

    /* getAllPriceModelsByRoutesTimeID
     * start
     *  */
//    @GetMapping("/getAllPriceByStatus")
//    public PriceResponse getAllPriceModelsByRoutesTime(@RequestParam UUID routesTimeId) {
//        log.debug("Inside getAllPriceModelsByStatus");
//        try {
//            List<PriceModel> list = priceService.getPriceByRoutesTime(routesTimeId);
//            PriceResponse response;
//            if(!list.isEmpty()){
//                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
//                return response;
//            }
//            return new PriceResponse("RoutesTime not found", false);
//        }catch (Exception e){
//            throw new BadRequestException(e.getMessage());
//        }
//    }
    /* getAllPriceModelsByRoutesTimeID
     * end
     *  */
}



