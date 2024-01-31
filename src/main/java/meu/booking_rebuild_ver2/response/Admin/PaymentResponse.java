package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse extends ResponseEntityExceptionHandler {
    private String message;
    private Boolean success;

    private List<PaymentDTO> paymentList;

    private PaymentDTO payment;


    public PaymentResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public PaymentResponse(String message, Boolean success, List<PaymentDTO> paymentList) {
        this.message = message;
        this.success = success;
        this.paymentList = paymentList;
    }


    public PaymentResponse(String message, Boolean success, PaymentDTO payment) {
        this.message = message;
        this.success = success;
        this.payment = payment;
    }

    public PaymentResponse(Boolean success, PaymentDTO payment) {
        this.success = success;
        this.payment = payment;
    }


}
