package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentTypesDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.PaymentTypesMapper;
import meu.booking_rebuild_ver2.model.Admin.PaymentTypesModel;

import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.PaymentTypeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.PaymentTypesResponse;


import meu.booking_rebuild_ver2.service.abstractions.Admin.IPaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentTypesService implements IPaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypesRepository;
    @Autowired
    private StatusRepository statusRepository;
    private PaymentTypesMapper paymentTypesMapper;

    @Autowired
    private UserID userID;

    @Override
    public PaymentTypesResponse createPaymentTypes(PaymentTypesDTO paymentTypesDTO) {
        try {
            if(statusRepository.existsById(paymentTypesDTO.getIdStatus())) {
                paymentTypesDTO.setIdUserConfig(userID.getUserValue().getId());
                PaymentTypesModel paymentTypes = PaymentTypesMapper.dtoToPaymentType(paymentTypesDTO);
                paymentTypesRepository.save(paymentTypes);
                return new PaymentTypesResponse(Constants.MESSAGE_ADDED_PAYMENT_TYPE_SUCCESSFULLY, true, paymentTypesDTO);
            }
            return new PaymentTypesResponse(Constants.MESSAGE_INVALID_DATA+ " status", false);

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public PaymentTypesResponse updatePaymentTypes(PaymentTypesDTO paymentTypesDTO) {
        PaymentTypesModel updateModel = paymentTypesRepository.findPaymentTypesModelById(paymentTypesDTO.getId());
        PaymentTypesResponse response;
        if(updateModel != null) {
            updateModel.setPaymentType(paymentTypesDTO.getPaymentType());
            updateModel.setStatus(new Status(paymentTypesDTO.getIdStatus()));
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(userID.getUserValue());
            paymentTypesRepository.save(updateModel);
            response = new PaymentTypesResponse(Constants.MESSAGE_UPDATED_PAYMENT_TYPE_SUCCESSFULLY, true, paymentTypesDTO);
            return response;
        }
        response = new PaymentTypesResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
        return response;
    }


    @Override
    public PaymentTypesResponse getAll() {
        try {
            List<PaymentTypesDTO> paymentTypesListDTO = paymentTypesRepository.findAll()
                    .stream()
                    .map(PaymentTypesMapper::paymentDTO)
                    .toList();
            return new PaymentTypesResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_TYPE_SUCCESS, true, paymentTypesListDTO);
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public PaymentTypesResponse getPaymentTypeByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<PaymentTypesDTO> list = paymentTypesRepository.getPaymentTypesByStatus(id)
                    .stream()
                    .map(PaymentTypesMapper::paymentDTO)
                    .toList();
            PaymentTypesResponse response;
            if (!list.isEmpty()) {
                response = new PaymentTypesResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PaymentTypesResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PaymentTypesResponse("Status "+ Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PaymentTypesResponse getPaymentTypesModelById(UUID id) {
        PaymentTypesResponse response;
        if(paymentTypesRepository.existsById(id)) {
            PaymentTypesModel model = paymentTypesRepository.findPaymentTypesModelById(id);
            PaymentTypesDTO paymentTypesDTO = PaymentTypesMapper.paymentDTO(model);
            response = new PaymentTypesResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_TYPE_SUCCESS, true, paymentTypesDTO);
            return response;
        }
        response = new PaymentTypesResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        return response;
    }

    @Override
    public PaymentTypesResponse deletePaymentTypesModelById(UUID id) {
        if(!paymentTypesRepository.existsById(id)){
            return new PaymentTypesResponse(Constants.MESSAGE_ID_NOT_FOUND,true );
        }
        else{
            paymentTypesRepository.deleteById(id);
            return new PaymentTypesResponse("PaymentType "+ Constants.MESSAGE_DELETED_SUCCESS,true );
        }

    }


}
