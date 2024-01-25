package meu.booking_rebuild_ver2.service.abstractions;

import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.response.StatusResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IStatusService {
    StatusResponse createStatus(Status status);
    StatusResponse updateStatus(Status status);
    StatusResponse getAll();
    StatusResponse getAllByFlag(boolean flag);

    StatusResponse getStatusById(UUID idStatus);

    StatusResponse deleteStatusById(UUID idStatus);
}
