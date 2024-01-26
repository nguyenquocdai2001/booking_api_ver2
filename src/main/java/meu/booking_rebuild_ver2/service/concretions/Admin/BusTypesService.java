package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.response.Admin.BusTypesResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IBusTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusTypesService implements IBusTypesService {
    @Autowired
    private BusTypesRepository busTypesRepository;
    @Override
    public BusTypesResponse createBusType(BusTypes busTypes) {
        try {
            if(busTypesRepository.findByLicensePlate(busTypes.getLicensePlate()) != null){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_DUPLICATE_LICENSE_PLATE_SUCCESS, false);
            }
            busTypesRepository.save(busTypes);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_ADD_SUCCESS, true, busTypes);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getAllBusTypes() {
        try {
            List<BusTypes> busTypesList = busTypesRepository.findAll();
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getAllBusTypesByNumberOfSeat(int numberOfSeat) {
        try {
            if(busTypesRepository.findALlByNumberOfSeat(numberOfSeat).isEmpty()){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_FAILED, false);
                return response;
            }
            List<BusTypes> busTypesList = busTypesRepository.findALlByNumberOfSeat(numberOfSeat);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
            return response;

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getAllBusTypesByStatus(UUID idStatus) {
        try {
            if(busTypesRepository.findAllByStatus(idStatus).isEmpty()){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_FAILED, false);
                return response;
            }
            List<BusTypes> busTypesList = busTypesRepository.findAllByStatus(idStatus);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
            return response;

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getBusTypesById(UUID id) {
        try {
            if(busTypesRepository.findById(id).isPresent()){
                Optional<BusTypes> busTypes = busTypesRepository.findById(id);
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_SUCCESS, true, busTypes);
                return response;
            }
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getBusTypesByLicensePlate(String licensePlate) {
        try {
            if((busTypesRepository.findByLicensePlate(licensePlate) == null)){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_FAILED, false);
                return response;
            }
            BusTypes busTypes = busTypesRepository.findByLicensePlate(licensePlate);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_SUCCESS, true, busTypes);
            return response;

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse updateBusType(BusTypes busTypes) {
        try {
            if(!busTypesRepository.findById(busTypes.getId()).isPresent()){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_FIND_BUS_TYPE_FAILED, false);
                return response;
            }
            BusTypes updatedBusType = busTypesRepository.findById(busTypes.getId()).get();
            updatedBusType.setType(busTypes.getType());
            updatedBusType.setLicensePlate(busTypes.getLicensePlate());
            updatedBusType.setNumberOfSeat(busTypes.getNumberOfSeat());
            updatedBusType.setStatus(busTypes.getStatus());
            updatedBusType.setIdUserConfig(busTypes.getIdUserConfig());
            busTypesRepository.save(updatedBusType);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_UPDATE_SUCCESS, true, updatedBusType);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse deleteBusTypesById(UUID id) {
        try {
            if(busTypesRepository.findById(id) == null){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            busTypesRepository.deleteById(id);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_DELETE_BUS_TYPE_SUCCESS, true);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
