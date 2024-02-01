package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.PriceDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.PriceMapper;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
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
    @Autowired
    private UserID userID;
    @Override
    public PriceResponse createPrice(PriceDTO priceDTO) {
        if(checkDate(priceDTO)){
            priceDTO.setIdUserConfig(userID.getUserValue().getId());
            PriceModel priceModel = PriceMapper.dtoToPrices(priceDTO);
            priceRepository.save(priceModel);
            return new PriceResponse(Constants.MESSAGE_STATUS_ADD_PRICE_SUCCESS, true, priceDTO);
        }
        return new PriceResponse(Constants.MESSAGE_INVALID_DATA+" status or BusType", false);
    }

    @Override
    public PriceResponse getAllPrice() {
        List<PriceDTO> list = priceRepository.findAll()
                .stream()
                .map(PriceMapper::priceDTO)
                .toList();
        return new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
    }

    @Override
    public PriceResponse updatePrice(PriceDTO priceDTO) {
        PriceModel updateModel = priceRepository.findPriceModelById(priceDTO.getId());
        PriceResponse response;
        if(updateModel != null && checkDate(priceDTO)) {
            updateModel.setPrice(priceDTO.getPrice());
            updateModel.setIdBusType(new BusTypes(priceDTO.getIdBusType()));
            updateModel.setIdRoutesTime(new RoutesTimeModel(priceDTO.getIdRoutesTime()));
            updateModel.setStatus(new Status(priceDTO.getIdStatus()));
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(userID.getUserValue());
            priceRepository.save(updateModel);
            response = new PriceResponse(Constants.MESSAGE_UPDATE_PRICE_SUCCESS, true, priceDTO);
            return response;
        }
        response = new PriceResponse("ID "+ Constants.MESSAGE_ID_NOT_FOUND, false);
        return response;
    }

    @Override
    public PriceResponse findByID(UUID id) {
        PriceResponse response;
        if(priceRepository.existsById(id)) {
            PriceModel model = priceRepository.findPriceModelById(id);
            PriceDTO priceDTO = PriceMapper.priceDTO(model);
            response = new PriceResponse(Constants.MESSAGE_PRICE_FIND_SUCCESS, true, priceDTO);
            return response;
        }
        response = new PriceResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        return response;
    }

    @Override
    public PriceResponse deleteById(UUID id) {
        if(!priceRepository.existsById(id)){
            return new PriceResponse(Constants.MESSAGE_ID_NOT_FOUND,true );
        }
        else{
            priceRepository.deleteById(id);
            return new PriceResponse("Price "+ Constants.MESSAGE_DELETED_SUCCESS,true );
        }

    }

    @Override
    public PriceResponse getPriceByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<PriceDTO> list = priceRepository.getPriceByStatus(id)
                    .stream()
                    .map(PriceMapper::priceDTO)
                    .toList();
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PriceResponse("Status "+ Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PriceResponse getPriceByBusType(UUID id) {
        if(busTypesRepository.existsById(id)) {
            List<PriceDTO> list = priceRepository.getPriceByBusType(id)
                    .stream()
                    .map(PriceMapper::priceDTO)
                    .toList();
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PriceResponse("BusType "+ Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public PriceResponse getPriceByRoutesTime(UUID id) {
        if(routesTimeRepository.existsById(id)) {
            List<PriceDTO> list = priceRepository.getPriceByRoutesTime(id)
                    .stream()
                    .map(PriceMapper::priceDTO)
                    .toList();
            PriceResponse response;
            if (!list.isEmpty()) {
                response = new PriceResponse(Constants.MESSAGE_STATUS_GET_ALL_PRICE_SUCCESS, true, list);
                return response;
            }
            response = new PriceResponse(Constants.MESSAGE_EMPTY_LIST, false);
            return response;
        }
        return new PriceResponse("RoutesTime "+ Constants.MESSAGE_ID_NOT_FOUND, false);

    }

    private boolean checkDate(PriceDTO priceModel){

        return statusRepository.existsById(priceModel.getIdStatus())
                && busTypesRepository.existsById(priceModel.getIdBusType())
                && routesTimeRepository.existsById(priceModel.getIdRoutesTime());
    }

}
