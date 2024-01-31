package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.*;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class PaymentMapper {
    public static PaymentDTO paymentDTO(PaymentModel paymentModel){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(paymentModel.getId());
        paymentDTO.setIdPaymentType(paymentModel.getIdPaymentTypes().getId());
        paymentDTO.setIdCustomer(paymentModel.getIdCustomer().getId());
        paymentDTO.setIdPrice(paymentModel.getIdPrice().getId());
        paymentDTO.setTimePaid(paymentModel.getTimePaid());
        paymentDTO.setIdStatus(paymentModel.getStatus().getId());
        paymentDTO.setIdUserConfig(paymentModel.getIdUserConfig().getId());

        return paymentDTO;
    }

    public static PaymentModel dtoToPayment(PaymentDTO paymentDTO){
        PaymentModel payment = new PaymentModel();
        payment.setId(paymentDTO.getId());
        payment.setIdPaymentTypes(new PaymentTypesModel(paymentDTO.getIdPaymentType()));
        payment.setIdCustomer(new Customer(paymentDTO.getIdCustomer()));
        payment.setIdPrice(new PriceModel(paymentDTO.getIdPrice()));
        payment.setTimePaid(paymentDTO.getTimePaid());
        payment.setStatus(new Status(paymentDTO.getIdStatus()));
        payment.setIdUserConfig(new User(paymentDTO.getIdUserConfig()));
        return payment;
    }
}
