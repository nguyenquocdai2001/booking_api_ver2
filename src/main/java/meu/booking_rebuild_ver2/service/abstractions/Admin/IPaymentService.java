package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import meu.booking_rebuild_ver2.response.Admin.PaymentResponse;

import java.util.UUID;

public interface IPaymentService {
    PaymentResponse createPayment(PaymentDTO paymentDTO);
    PaymentResponse updatePayment(PaymentDTO paymentDTO);
    PaymentResponse getAll();
    PaymentResponse getPaymentByStatus(UUID id);
    PaymentResponse getPaymentByPaymentType(UUID id);
    PaymentResponse getPaymentByCustomer(UUID id);
    PaymentResponse getPaymentByPrice(UUID id);
    PaymentResponse getPaymentModelById(UUID id);
    PaymentResponse deletePaymentModelById(UUID id);
}
