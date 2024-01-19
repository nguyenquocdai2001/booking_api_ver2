package meu.booking_rebuild_ver2.controller;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.concretions.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    @Autowired
    private StatusService statusService;


    @PostMapping (path = "/addStatus")
    public StatusResponse addStatusPostMapping(@RequestBody @Valid Status status){
        try {
            statusService.createStatus(status);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_ADD_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllStatus")
    public ResponseEntity<StatusResponse> getAllStatus(){
        try {
            List<Status> statusList = statusService.getAll();
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllStatusByFlag")
    public StatusResponse getAllStatusByFlag(Boolean flag){
        try {
            List<Status> statusList = statusService.getAllByFlag(flag);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getStatusById")
    public StatusResponse getStatusById(UUID id){
        try {
            if(statusService.getStatusById(id).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            Status status = statusService.getStatusById(id).get();
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "updateStatus")
    public StatusResponse updateStatus(@RequestBody @Valid Status status){
        try {
            if(statusService.getStatusById(status.getId()).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            statusService.updateStatus(status);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_UPDATE_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @DeleteMapping(path = "deleteStatusById")
    public StatusResponse deleteStatusById(UUID idStatus){
        try {
            if(statusService.getStatusById(idStatus).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            statusService.deleteStatusById(idStatus);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_DELETE_STATUS_SUCCESS, true);

            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
