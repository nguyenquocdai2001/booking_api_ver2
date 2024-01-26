package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.PriceRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.PriceResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PriceService implements IPriceService {
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    BusTypesRepository busTypesRepository;
    @Autowired
    RoutesTimeRepository routesTimeRepository;
    @Override
    public PriceResponse createPrice(PriceModel priceModel) {
        PriceResponse response;
        if(checkDate(priceModel)){
            priceRepository.save(priceModel);
            return new PriceResponse(Constants.MESSAGE_STATUS_ADD_PRICE_SUCCESS, true, priceModel);
        }
        return new PriceResponse("Invalid status or BusType", false);
    }

    @Override
    public PriceResponse getAllPrice() {
        List<PriceModel> list = priceRepository.findAll();
        return new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
    }

    @Override
    public PriceResponse updatePrice(PriceModel priceModel) {
        PriceModel updateModel = priceRepository.findPriceModelById(priceModel.getId());
        PriceResponse response;

        if(updateModel != null && checkDate(updateModel)) {
            updateModel.setPrice(priceModel.getPrice());
            updateModel.setIdBusType(priceModel.getIdBusType());
            updateModel.setIdRoutesTime(priceModel.getIdRoutesTime());
            updateModel.setStatus(priceModel.getStatus());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(priceModel.getIdUserConfig());
            priceRepository.save(updateModel);
            response = new PriceResponse(Constants.MESSAGE_UPDATE_PRICE_SUCCESS, true, updateModel);
            return response;
        }
        response = new PriceResponse("ID not found", false);
        return response;
    }

    @Override
    public PriceResponse findByID(UUID id) {
        PriceResponse response;
        if(priceRepository.existsById(id)) {
            PriceModel model = priceRepository.findPriceModelById(id);
            response = new PriceResponse(Constants.MESSAGE_PRICE_FIND_SUCCESS, true, model);
            return response;
        }
        response = new PriceResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        return response;
    }

    @Override
    public PriceResponse deleteById(UUID id) {
        if(!priceRepository.existsById(id)){
            return new PriceResponse("Invalid ID",true );
        }
        else{
            priceRepository.deleteById(id);
            return new PriceResponse("Delete Price Success",true );
        }

    }

    @Override
    public PriceResponse getPriceByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<PriceModel> list = priceRepository.getPriceByStatus(id);
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse("List is empty", false);
            return response;
        }
        return new PriceResponse("Status not found", false);
    }

    @Override
    public PriceResponse getPriceByBusType(UUID id) {
        if(busTypesRepository.existsById(id)) {
            List<PriceModel> list = priceRepository.getPriceByBusType(id);
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse("List is empty", false);
            return response;
        }
        return new PriceResponse("BusType not found", false);
    }

    @Override
    public PriceResponse getPriceByRoutesTime(UUID id) {
        if(routesTimeRepository.existsById(id)) {
            List<PriceModel> list = priceRepository.getPriceByRoutesTime(id);
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse("List is empty", false);
            return response;
        }
        return new PriceResponse("RoutesTime not found", false);

    }

    private boolean checkDate(PriceModel priceModel){

        return statusRepository.existsById(priceModel.getStatus().getId())
                && busTypesRepository.existsById(priceModel.getIdBusType().getId())
                && routesTimeRepository.existsById(priceModel.getIdRoutesTime().getId());
    }

}
