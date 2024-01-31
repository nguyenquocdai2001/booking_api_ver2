
package meu.booking_rebuild_ver2.controller.Admin;

import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import meu.booking_rebuild_ver2.response.Admin.PaymentResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-6
 * */
@Slf4j
@RestController
@RequestMapping(path = "/payment",  produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    /* addNewPayment
     * start
     *  */
    @PostMapping("/addPayment")
    public PaymentResponse addPayment(@RequestBody PaymentDTO paymentDTO){
        log.debug("Inside addPayment function()");
        try {
            return paymentService.createPayment(paymentDTO);
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* addNewPayment
     * end
     * */


    /* getAllPayment
     * start
     *  */
    @GetMapping("/getAllPayment")
    public PaymentResponse getAllPaymentModels() {
        log.debug("Inside getAllPaymentModels");
        try {
            return paymentService.getAll();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPayment
     * end
     *  */


    /* getPaymentById
     * start
     *  */
    @GetMapping("/getPaymentById")
    public PaymentResponse getPaymentModelById(@RequestParam UUID id) {
        log.debug("Inside getPaymentModelByID");
        try {
            return paymentService.getPaymentModelById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getPaymentById
     * end
     *  */


    /* updatePaymentById
     * start
     *  */
    @PutMapping("/updatePayment")
    public PaymentResponse updatePaymentModelById(@RequestBody PaymentDTO paymentDTO) {
        log.debug("Inside updatePaymentModelById");
        try {
            return paymentService.updatePayment(paymentDTO);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    /* updatePaymentById
     * end
     *  */

    /* getAllPaymentByStatusID
     * start
     *  */
    @GetMapping("/getByStatus")
    public PaymentResponse getAllPaymentModelsByStatus(@RequestParam UUID statusId) {
        log.debug("Inside getAllPaymentModelsByStatus");
        try {
            return paymentService.getPaymentByStatus(statusId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentByStatusID
     * end
     *  */

    /* getAllPaymentModelsByPrice
     * start
     *  */
    @GetMapping("/getByPrice")
    public PaymentResponse getAllPaymentModelsByPrice(@RequestParam UUID priceId) {
        log.debug("Inside getAllPaymentModelsByPrice");
        try {
            return paymentService.getPaymentByPrice(priceId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentModelsByPrice
     * end
     *  */

    /* getAllPaymentModelsByCustomer
     * start
     *  */
    @GetMapping("/getByCustomer")
    public PaymentResponse getAllPaymentModelsByCustomer(@RequestParam UUID customerId) {
        log.debug("Inside getAllPaymentModelsByCustomer");
        try {
            return paymentService.getPaymentByCustomer(customerId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentModelsByCustomer
     * end
     *  */

    /* getAllPaymentModelsByCustomer
     * start
     *  */
    @GetMapping("/getByPaymentType")
    public PaymentResponse getAllPaymentModelsByPaymentType(@RequestParam UUID paymentTypeId) {
        log.debug("Inside getAllPaymentModelsByCustomer");
        try {
            return paymentService.getPaymentByPaymentType(paymentTypeId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    /* getAllPaymentModelsByCustomer
     * end
     *  */

    /*
     * deleteByid
     *
     * */
    @DeleteMapping("deletePaymentById")
    public PaymentResponse deletePaymentModel(@RequestParam UUID id) {
        log.debug("Inside deletePaymentModel");
        try {
            return paymentService.deletePaymentModelById(id);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }




}



