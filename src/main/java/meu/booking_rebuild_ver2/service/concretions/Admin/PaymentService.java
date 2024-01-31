package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.PaymentDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.PaymentMapper;
import meu.booking_rebuild_ver2.model.Admin.PaymentModel;
import meu.booking_rebuild_ver2.model.Admin.PaymentTypesModel;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.PaymentRepository;
import meu.booking_rebuild_ver2.repository.Admin.PaymentTypeRepository;
import meu.booking_rebuild_ver2.repository.Admin.PriceRepository;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.PaymentResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PriceRepository priceRepository;
    private PaymentMapper paymentMapper;

    @Autowired
    private UserID userID;

    @Override
    public PaymentResponse createPayment(PaymentDTO paymentDTO) {
        try {
            if(checkData(paymentDTO)) {
                paymentDTO.setIdUserConfig(userID.getUserValue().getId());
                PaymentModel payment = PaymentMapper.dtoToPayment(paymentDTO);
                paymentRepository.save(payment);
                return new PaymentResponse(Constants.MESSAGE_ADDED_PAYMENT_SUCCESSFULLY, true, paymentDTO);
            }
            return new PaymentResponse(Constants.MESSAGE_INVALID_DATA, false);

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public PaymentResponse updatePayment(PaymentDTO paymentDTO) {
        PaymentModel updateModel = paymentRepository.findPaymentModelById(paymentDTO.getId());
        PaymentResponse response;
        if(updateModel != null && checkData(paymentDTO)) {
            updateModel.setIdCustomer(new Customer(paymentDTO.getIdCustomer()));
            updateModel.setIdPaymentTypes(new PaymentTypesModel(paymentDTO.getIdPaymentType()));
            updateModel.setIdPrice(new PriceModel(paymentDTO.getIdPrice()));
            updateModel.setTimePaid(paymentDTO.getTimePaid());
            updateModel.setStatus(new Status(paymentDTO.getIdStatus()));
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(userID.getUserValue());
            paymentRepository.save(updateModel);
            response = new PaymentResponse(Constants.MESSAGE_UPDATED_PAYMENT_SUCCESSFULLY, true, paymentDTO);
            return response;
        }
        response = new PaymentResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
        return response;
    }


    @Override
    public PaymentResponse getAll() {
        try {
            List<PaymentDTO> paymentListDTO = paymentRepository.findAll()
                    .stream()
                    .map(PaymentMapper::paymentDTO)
                    .toList();
            return new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, paymentListDTO);
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public PaymentResponse getPaymentByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<PaymentDTO> list = paymentRepository.getPaymentByStatus(id)
                    .stream()
                    .map(PaymentMapper::paymentDTO)
                    .toList();
            PaymentResponse response;
            if (!list.isEmpty()) {
                response = new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, list);
                return response;
            }
            response = new PaymentResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PaymentResponse("Status "+Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PaymentResponse getPaymentByPaymentType(UUID id) {
        if(paymentTypeRepository.existsById(id)) {
            List<PaymentDTO> list = paymentRepository.getPaymentByPaymentTypes(id)
                    .stream()
                    .map(PaymentMapper::paymentDTO)
                    .toList();
            PaymentResponse response;
            if (!list.isEmpty()) {
                response = new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, list);
                return response;
            }
            response = new PaymentResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PaymentResponse("Payment Type " + Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PaymentResponse getPaymentByCustomer(UUID id) {
        if(customerRepository.existsById(id)) {
            List<PaymentDTO> list = paymentRepository.getPaymentByCustomer(id)
                    .stream()
                    .map(PaymentMapper::paymentDTO)
                    .toList();
            PaymentResponse response;
            if (!list.isEmpty()) {
                response = new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, list);
                return response;
            }
            response = new PaymentResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PaymentResponse("Customer " + Constants.MESSAGE_ID_NOT_FOUND , false);
    }

    @Override
    public PaymentResponse getPaymentByPrice(UUID id) {
        if(priceRepository.existsById(id)) {
            List<PaymentDTO> list = paymentRepository.getPaymentByPrice(id)
                    .stream()
                    .map(PaymentMapper::paymentDTO)
                    .toList();
            PaymentResponse response;
            if (!list.isEmpty()) {
                response = new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, list);
                return response;
            }
            response = new PaymentResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PaymentResponse("Price " + Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PaymentResponse getPaymentModelById(UUID id) {
        PaymentResponse response;
        if(paymentRepository.existsById(id)) {
            PaymentModel model = paymentRepository.findPaymentModelById(id);
            PaymentDTO paymentDTO = PaymentMapper.paymentDTO(model);
            response = new PaymentResponse(Constants.MESSAGE_STATUS_GET_PAYMENT_SUCCESS, true, paymentDTO);
            return response;
        }
        response = new PaymentResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
        return response;
    }

    @Override
    public PaymentResponse deletePaymentModelById(UUID id) {
        if(!paymentRepository.existsById(id)){
            return new PaymentResponse(Constants.MESSAGE_ID_NOT_FOUND,false );
        }
        else{
            paymentRepository.deleteById(id);
            return new PaymentResponse(Constants.MESSAGE_DELETE_PAYMENT_SUCCESSFULLY,true );
        }

    }
    private boolean checkData(PaymentDTO paymentDTO){
       return paymentTypeRepository.existsById(paymentDTO.getIdPaymentType())
                && statusRepository.existsById(paymentDTO.getIdStatus())
               && priceRepository.existsById(paymentDTO.getIdPrice())
               && customerRepository.existsById(paymentDTO.getIdCustomer());

    }

}
