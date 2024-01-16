package meu.booking_rebuild_ver2.controller;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.StatusResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    private final StatusRepository statusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @PutMapping (path = "/addStatus")
    public StatusResponse addStatusPutMapping(@RequestBody @Valid Status status){
        try {
            status.setFlag(true);
            statusRepository.save(status);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_ADD_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping (path = "/addStatus")
    public StatusResponse addStatusPostMapping(@RequestBody @Valid Status status){
        try {
            status.setFlag(true);
            statusRepository.save(status);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_ADD_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllStatus")
    public StatusResponse getAllStatus(){
        try {
            List<Status> statusList = statusRepository.findAll();
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "getAllStatusByFlag")
    public StatusResponse getAllStatusByFlag(@RequestBody @Valid Boolean flag){
        try {
            List<Status> statusList = statusRepository.findAllByFlag(flag);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "getStatusById")
    public StatusResponse getStatusById(@RequestBody @Valid UUID id){
        try {
            if(statusRepository.findStatusById(id) == null){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            Status status = statusRepository.findStatusById(id);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "updateStatus")
    public StatusResponse updateStatus(@RequestBody @Valid Status status){
        try {
            if(statusRepository.findStatusById(status.getId()) == null){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            Status updatedStatus = statusRepository.findStatusById(status.getId());
            updatedStatus.setStatus(status.getStatus());
            updatedStatus.setFlag(status.isFlag());
            statusRepository.save(updatedStatus);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_UPDATE_STATUS_SUCCESS, true, updatedStatus);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @DeleteMapping(path = "deleteStatus")
    public StatusResponse deleteStatus(@RequestBody @Valid Status status){
        try {
            if(statusRepository.findStatusById(status.getId()) == null){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            statusRepository.deleteById(status.getId());
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_DELETE_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
