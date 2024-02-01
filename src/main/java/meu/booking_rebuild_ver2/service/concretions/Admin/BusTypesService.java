package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusTypeDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.BusTypeMapper;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.BusTypesResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IBusTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-9
 * */
@Service
public class BusTypesService implements IBusTypesService {
    @Autowired
    UserID userID;
    @Autowired
    private BusTypesRepository busTypesRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public BusTypesResponse createBusType(BusTypeDTO busTypesDTO) {
        try {
            if(busTypesRepository.findByLicensePlate(busTypesDTO.getLicensePlate()) != null){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_DUPLICATE_LICENSE_PLATE, false);
                return response;
            }
            busTypesDTO.setIdUserConfig(userID.getUserValue().getId());
            BusTypes busTypes = BusTypeMapper.dtoToBusTypes(busTypesDTO);

            Status status = statusRepository.findStatusById(busTypesDTO.getIdStatus());
            busTypes.setStatus(status);

            busTypesRepository.save(busTypes);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_ADD_SUCCESS, true, busTypesDTO);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse getAllBusTypes() {
        try {
            List<BusTypeDTO> busTypesListDTO = busTypesRepository.findAll()
                    .stream()
                    .map(BusTypeMapper::busTypeDTO)
                    .toList();
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesListDTO);
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
            List<BusTypeDTO> busTypesListDTO = busTypesRepository.findALlByNumberOfSeat(numberOfSeat)
                    .stream()
                    .map(BusTypeMapper::busTypeDTO)
                    .toList();
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesListDTO);
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
            List<BusTypeDTO> busTypesListDTO = busTypesRepository.findAllByStatus(idStatus)
                    .stream()
                    .map(BusTypeMapper::busTypeDTO)
                    .toList();
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesListDTO);
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
                BusTypeDTO busTypeDTO = BusTypeMapper.busTypeDTO(busTypes.get());
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_SUCCESS, true, busTypeDTO);
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
            BusTypeDTO busTypeDTO = BusTypeMapper.busTypeDTO(busTypes);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_SUCCESS, true, busTypeDTO);
            return response;

        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusTypesResponse updateBusType(BusTypeDTO busTypeDTO) {
        try {
            if(!busTypesRepository.findById(busTypeDTO.getId()).isPresent()){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_FIND_BUS_TYPE_FAILED, false);
                return response;
            }
            BusTypes updatedBusType = busTypesRepository.findById(busTypeDTO.getId()).get();
            updatedBusType.setType(busTypeDTO.getType());
            updatedBusType.setLicensePlate(busTypeDTO.getLicensePlate());
            updatedBusType.setNumberOfSeat(busTypeDTO.getNumberOfSeat());
            Status status = statusRepository.findStatusById(busTypeDTO.getIdStatus());
            updatedBusType.setStatus(status);
            updatedBusType.setIdUserConfig(userID.getUserValue());
            busTypesRepository.save(updatedBusType);

            busTypeDTO.setIdUserConfig(userID.getUserValue().getId());
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_UPDATE_SUCCESS, true, busTypeDTO);
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
