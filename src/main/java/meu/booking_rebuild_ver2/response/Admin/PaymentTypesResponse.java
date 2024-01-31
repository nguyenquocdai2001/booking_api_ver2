package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentTypesDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypesResponse extends ResponseEntityExceptionHandler {
    private String message;
    private Boolean success;

    private List<PaymentTypesDTO> paymentTypesList;

    private PaymentTypesDTO paymentTypes;


    public PaymentTypesResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public PaymentTypesResponse(String message, Boolean success, List<PaymentTypesDTO> paymentTypesList) {
        this.message = message;
        this.success = success;
        this.paymentTypesList = paymentTypesList;
    }


    public PaymentTypesResponse(String message, Boolean success, PaymentTypesDTO paymentTypes) {
        this.message = message;
        this.success = success;
        this.paymentTypes = paymentTypes;
    }

    public PaymentTypesResponse(Boolean success, PaymentTypesDTO paymentTypes) {
        this.success = success;
        this.paymentTypes = paymentTypes;
    }


}
