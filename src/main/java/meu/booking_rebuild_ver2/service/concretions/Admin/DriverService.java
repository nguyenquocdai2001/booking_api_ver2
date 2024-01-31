package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.DriverDTO;
import meu.booking_rebuild_ver2.model.Admin.Driver;
import meu.booking_rebuild_ver2.model.Admin.Mapper.DriverMapper;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.DriverRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.DriverResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DriverService implements IDriverService {
    @Autowired
    UserID userID;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private BusTypesRepository busTypesRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public DriverResponse createDriver(DriverDTO driverDTO) {
        try {
            if (driverRepository.findByPhone(driverDTO.getPhone()) != null) {
                DriverResponse response = new DriverResponse(Constants.MESSAGE_DRIVER_DUPLICATE_PHONE, false);
                return response;
            }
            driverDTO.setIdUserConfig(userID.getUserValue().getId());
            Driver driver = DriverMapper.dtoToDriver(driverDTO);

            BusTypes busTypes = busTypesRepository.findById(driverDTO.getIdBusTypes().getId()).get();
            driver.setIdBusTypes(busTypes);

            Status status = statusRepository.findStatusById(driverDTO.getIdStatus());
            driver.setStatus(status);

            driverRepository.save(driver);
            DriverResponse response = new DriverResponse(Constants.MESSAGE_ADDED_DRIVER_SUCCESS, true, driverDTO);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse getAllDriver() {
        try {
            List<DriverDTO> driverListDTO = driverRepository.findAll()
                    .stream()
                    .map(DriverMapper::driverToDTO)
                    .toList();
            DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_ALL_DRIVER_SUCCESS, true, driverListDTO);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse getAllByIdBusTypes(UUID idBusType) {
        try {
            if(driverRepository.findAllByIdBusTypes(idBusType) == null){
                DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_FAILED, false);
                return response;
            }
            List<DriverDTO> driverDTOList = driverRepository.findAllByIdBusTypes(idBusType)
                    .stream()
                    .map(DriverMapper::driverToDTO)
                    .toList();
            DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_SUCCESS, true, driverDTOList);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse getByPhone(String phone) {
        try {
            if (driverRepository.findByPhone(phone) == null) {
                DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_FAILED, false);
                return response;
            }
            DriverDTO driverDTO = DriverMapper.driverToDTO(driverRepository.findByPhone(phone));
            DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_SUCCESS, true, driverDTO);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse getByKindOfLicense(String kindOfLicense) {
        try {
            if (driverRepository.findAllByKindOfLicense(kindOfLicense) == null) {
                DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_FAILED, false);
                return response;
            }
            DriverDTO driverDTO = DriverMapper.driverToDTO(driverRepository.findAllByKindOfLicense(kindOfLicense));
            DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_SUCCESS, true, driverDTO);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse getDriverById(UUID idDriver) {
        try {
            if(!driverRepository.existsById(idDriver)){
                DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_FAILED, false);
                return response;
            }
            DriverDTO driverDTO = DriverMapper.driverToDTO(driverRepository.findById(idDriver).get());
            DriverResponse response = new DriverResponse(Constants.MESSAGE_GET_DRIVER_SUCCESS, true, driverDTO);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse updateDriver(DriverDTO driverDTO) {
        try {
            if(!driverRepository.existsById(driverDTO.getId())){
                DriverResponse response = new DriverResponse(Constants.MESSAGE_DRIVER_NULL, false);
                return response;
            }
            Driver driverUpdated = driverRepository.findById(driverDTO.getId()).get();
            driverUpdated.setName(driverDTO.getName());
            driverUpdated.setPhone(driverDTO.getPhone());
            driverUpdated.setKindOfLicense(driverDTO.getKindOfLicense());
            BusTypes busTypes = busTypesRepository.findById(driverDTO.getIdBusTypes().getId()).get();
            driverUpdated.setIdBusTypes(busTypes);

            Status status = statusRepository.findStatusById(driverDTO.getIdStatus());
            driverUpdated.setStatus(status);

            driverUpdated.setIdUserConfig(userID.getUserValue());

            driverRepository.save(driverUpdated);

            driverDTO.setIdUserConfig(userID.getUserValue().getId());
            DriverResponse response = new DriverResponse(Constants.MESSAGE_UPDATED_DRIVER_SUCCESS, true, driverDTO);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public DriverResponse deleteDriverById(UUID idDriver) {
        try {
            if(!driverRepository.existsById(idDriver)){
                DriverResponse response = new DriverResponse(Constants.MESSAGE_DRIVER_NULL, false);
                return response;
            }
            driverRepository.deleteById(idDriver);
            DriverResponse response = new DriverResponse(Constants.MESSAGE_DELETED_DRIVER_SUCCESS, true);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
