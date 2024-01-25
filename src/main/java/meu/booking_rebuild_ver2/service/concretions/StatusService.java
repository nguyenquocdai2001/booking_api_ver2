package meu.booking_rebuild_ver2.service.concretions;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.abstractions.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusService implements IStatusService {
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public StatusResponse createStatus(Status status) {
        try {
            statusRepository.save(status);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_ADD_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public StatusResponse getAll() {
        try {
            List<Status> statusList = statusRepository.findAll();
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public StatusResponse getAllByFlag(boolean flag) {
        try {
            List<Status> statusList = statusRepository.findAllByFlag(flag);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_ALL_SUCCESS, true, statusList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public StatusResponse getStatusById(UUID idStatus) {
        try {
            if(statusRepository.findById(idStatus).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            Status status = statusRepository.findById(idStatus).get();
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public StatusResponse updateStatus(Status status) {
        try {
            if(statusRepository.findById(status.getId()).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            Status updatedStatus = statusRepository.findById(status.getId()).get();
            updatedStatus.setStatus(status.getStatus());
            updatedStatus.setFlag(status.isFlag());
            statusRepository.save(updatedStatus);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_UPDATE_STATUS_SUCCESS, true, status);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public StatusResponse deleteStatusById(UUID idStatus) {
        try {
            if(statusRepository.findById(idStatus).isEmpty()){
                StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_FIND_STATUS_FAILED, false);
                return response;
            }
            statusRepository.deleteById(idStatus);
            StatusResponse response = new StatusResponse(Constants.MESSAGE_STATUS_DELETE_STATUS_SUCCESS, true);

            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
