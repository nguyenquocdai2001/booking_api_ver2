package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentTypesDTO;
import meu.booking_rebuild_ver2.model.Admin.PaymentTypesModel;
import meu.booking_rebuild_ver2.response.Admin.PaymentTypesResponse;
import meu.booking_rebuild_ver2.response.Admin.PriceResponse;

import java.util.UUID;

public interface IPaymentTypeService {
    PaymentTypesResponse createPaymentTypes(PaymentTypesDTO paymentTypesDTO);
    PaymentTypesResponse updatePaymentTypes(PaymentTypesDTO paymentTypesDTO);
    PaymentTypesResponse getAll();
    PaymentTypesResponse getPaymentTypeByStatus(UUID id);
    PaymentTypesResponse getPaymentTypesModelById(UUID id);

    PaymentTypesResponse deletePaymentTypesModelById(UUID id);
}
