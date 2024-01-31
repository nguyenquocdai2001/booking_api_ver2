package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentTypesDTO;
import meu.booking_rebuild_ver2.model.Admin.PaymentModel;
import meu.booking_rebuild_ver2.model.Admin.PaymentTypesModel;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class PaymentTypesMapper {
    public static PaymentTypesDTO paymentDTO(PaymentTypesModel paymentTypeModel){
        PaymentTypesDTO paymentTypeDTO = new PaymentTypesDTO();
        paymentTypeDTO.setId(paymentTypeModel.getId());
        paymentTypeDTO.setPaymentType(paymentTypeModel.getPaymentType());
        paymentTypeDTO.setIdStatus(paymentTypeModel.getStatus().getId());
        paymentTypeDTO.setIdUserConfig(paymentTypeModel.getIdUserConfig().getId());

        return paymentTypeDTO;
    }

    public static PaymentTypesModel dtoToPaymentType(PaymentTypesDTO paymentTypeDTO){
        PaymentTypesModel paymentType = new PaymentTypesModel();
        paymentType.setId(paymentTypeDTO.getId());
        paymentType.setPaymentType(paymentTypeDTO.getPaymentType());
        paymentType.setStatus(new Status(paymentTypeDTO.getIdStatus()));
        paymentType.setIdUserConfig(new User(paymentTypeDTO.getIdUserConfig()));
        return paymentType;
    }
}
