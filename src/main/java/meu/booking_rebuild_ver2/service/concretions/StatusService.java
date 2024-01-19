package meu.booking_rebuild_ver2.service.concretions;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.abstractions.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatusService implements IStatusService {
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public void createStatus(Status status) {
        statusRepository.save(status);
    }

    @Override
    public List<Status> getAll() {
        List<Status> statusList = statusRepository.findAll();
        return statusList;
    }

    @Override
    public List<Status> getAllByFlag(boolean flag) {
        List<Status> statusList = statusRepository.findAllByFlag(flag);
        return statusList;
    }

    @Override
    public Optional<Status> getStatusById(UUID idStatus) {
        if(statusRepository.existsById(idStatus)){
            Optional<Status> status = statusRepository.findById(idStatus);
            return status;
        }
        return Optional.empty();
    }

    @Override
    public void updateStatus(Status status) {
        if(statusRepository.existsById(status.getId())){
            Status updatedStatus = statusRepository.findById(status.getId()).get();
            updatedStatus.setStatus(status.getStatus());
            updatedStatus.setFlag(status.isFlag());
            statusRepository.save(updatedStatus);
        }
    }

    @Override
    public void deleteStatusById(UUID idStatus) {
        if(statusRepository.existsById(idStatus)){
            statusRepository.deleteById(idStatus);
        }
    }
}
