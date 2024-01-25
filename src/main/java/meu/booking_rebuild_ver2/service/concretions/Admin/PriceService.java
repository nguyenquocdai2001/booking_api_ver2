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
        /* checkPriceInput start*/
        PriceResponse response;
        /* checkPriceInput end*/
        if(checkDate(priceModel)){
            priceRepository.save(priceModel);
            return new PriceResponse(Constants.MESSAGE_STATUS_ADD_PRICE_SUCCESS, true, priceModel);
        }
        return new PriceResponse("Invalid status or BusType", false);
    }

    @Override
    public List<PriceModel> getAllPrice() {
        return priceRepository.findAll();
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
    public PriceModel findByID(UUID id) {
        if(priceRepository.existsById(id)) {
            return priceRepository.findPriceModelById(id);
        }
        return null;
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
    public List<PriceModel> getPriceByStatus(UUID id) {
        return priceRepository.getPriceByStatus(id);
    }

    @Override
    public List<PriceModel> getPriceByBusType(UUID id) {
        return priceRepository.getPriceByBusType(id);
    }

    @Override
    public List<PriceModel> getPriceByRoutesTime(UUID id) {
        return priceRepository.getPriceByRoutesTime(id);
    }

    private boolean checkDate(PriceModel priceModel){

        return statusRepository.existsById(priceModel.getStatus().getId())
                && busTypesRepository.existsById(priceModel.getIdBusType().getId())
                && routesTimeRepository.existsById(priceModel.getIdRoutesTime().getId());
    }

}
