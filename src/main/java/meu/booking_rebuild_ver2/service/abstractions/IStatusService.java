package meu.booking_rebuild_ver2.service.abstractions;

import meu.booking_rebuild_ver2.model.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStatusService {
    void createStatus(Status status);
    void updateStatus(Status status);
    List<Status> getAll();
    List<Status> getAllByFlag(boolean flag);

    Optional<Status> getStatusById(UUID idStatus);

    void deleteStatusById(UUID idStatus);
}
