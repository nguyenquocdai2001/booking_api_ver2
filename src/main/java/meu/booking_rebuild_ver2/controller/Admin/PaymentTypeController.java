
package meu.booking_rebuild_ver2.controller.Admin;

import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentTypesDTO;
import meu.booking_rebuild_ver2.response.Admin.PaymentTypesResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.PaymentTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-7
 * */
@Slf4j
@RestController
@RequestMapping(path = "/paymentType",  produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentTypeController {
    @Autowired
    PaymentTypesService paymentTypesService;

    /* addNewPaymentTypes
     * start
     *  */
    @PostMapping("/addPaymentTypes")
    public PaymentTypesResponse addPaymentTypes(@RequestBody PaymentTypesDTO model){
        log.debug("Inside addPaymentTypes function()");
        try {
            return paymentTypesService.createPaymentTypes(model);
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* addNewPaymentTypes
     * end
     * */


    /* getAllPaymentTypes
     * start
     *  */
    @GetMapping("/getAllPaymentTypes")
    public PaymentTypesResponse getAllPaymentTypesModels() {
        log.debug("Inside getAllPaymentTypesModels");
        try {
            return paymentTypesService.getAll();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentTypes
     * end
     *  */


    /* getPaymentTypesById
     * start
     *  */
    @GetMapping("/getPaymentTypesById")
    public PaymentTypesResponse getPaymentTypesModelById(@RequestParam UUID id) {
        log.debug("Inside getPaymentTypesModelByID");
        try {
            return paymentTypesService.getPaymentTypesModelById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getPaymentTypesById
     * end
     *  */


    /* updatePaymentTypesById
     * start
     *  */
    @PutMapping("/updatePaymentTypes")
    public PaymentTypesResponse updatePaymentTypesModelById(@RequestBody PaymentTypesDTO paymentTypesDTO) {
        log.debug("Inside updatePaymentTypesModelById");
        try {
            return paymentTypesService.updatePaymentTypes(paymentTypesDTO);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* updatePaymentTypesById
     * end
     *  */

    /* getAllPaymentTypesByStatusID
     * start
     *  */
    @GetMapping("/getByStatus")
    public PaymentTypesResponse getAllPaymentTypesModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllPaymentTypesModelsByStatus");
        try {
            return paymentTypesService.getPaymentTypeByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentTypesByStatusID
     * end
     *  */

    /*
     * deleteByid
     *
     * */
    @DeleteMapping("deletePaymentTypesById")
    public PaymentTypesResponse deletePaymentTypesModel(@RequestParam UUID id) {
        log.debug("Inside deletePaymentTypesModel");
        try {
            return paymentTypesService.deletePaymentTypesModelById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }




}



