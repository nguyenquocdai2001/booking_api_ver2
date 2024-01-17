package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.response.Admin.BusTypesResponse;
import meu.booking_rebuild_ver2.response.StatusResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/busTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusTypesController {
    private final BusTypesRepository busTypesRepository;

    public BusTypesController(BusTypesRepository busTypesRepository) {
        this.busTypesRepository = busTypesRepository;
    }

    @PostMapping(path = "/addBusTypes")
    public BusTypesResponse addBusTypesPostMapping(@RequestBody @Valid BusTypes busTypes){
        try {
            busTypesRepository.save(busTypes);
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_ADD_SUCCESS, true, busTypes);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllBusTypes")
    public BusTypesResponse getAllBusTypes(){
        try {
            List<BusTypes> busTypesList = busTypesRepository.findAll();
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllBusTypesByNumberOfSeat")
    public BusTypesResponse getAllBusTypesByNumberOfSeat(int numberOfSeat){
        try {
            if(!busTypesRepository.findALlByNumberOfSeat(numberOfSeat).isEmpty()){
                List<BusTypes> busTypesList = busTypesRepository.findALlByNumberOfSeat(numberOfSeat);
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
                return response;
            }
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllBusTypesByStatus")
    public BusTypesResponse getAllBusTypesByStatus(Status status){
        try {
            if(!busTypesRepository.findAllByStatus(status).isEmpty()){
                List<BusTypes> busTypesList = busTypesRepository.findAllByStatus(status);
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_SUCCESS, true, busTypesList);
                return response;
            }
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPES_FIND_ALL_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getBusTypesById")
    public BusTypesResponse getBusTypesById(UUID id){
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

    @GetMapping(path = "getBusTypesByLicensePlate")
    public BusTypesResponse getBusTypesByLicensePlate(String licensePlate){
        try {
            if(!(busTypesRepository.findByLicensePlate(licensePlate) == null)){
                BusTypes busTypes = busTypesRepository.findByLicensePlate(licensePlate);
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_SUCCESS, true, busTypes);
                return response;
            }
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_BUS_TYPE_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "updateBusType")
    public BusTypesResponse updateBusType(@RequestBody @Valid BusTypes busTypes){
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

    @DeleteMapping(path = "deleteBusTypesById")
    public BusTypesResponse deleteBusTypesById(@RequestBody @Valid BusTypes busTypes){
        try {
            if(busTypesRepository.findById(busTypes.getId()) == null){
                BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            busTypesRepository.deleteById(busTypes.getId());
            BusTypesResponse response = new BusTypesResponse(Constants.MESSAGE_DELETE_BUS_TYPE_SUCCESS, true, busTypes);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
