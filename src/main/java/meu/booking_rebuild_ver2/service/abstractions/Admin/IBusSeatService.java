package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusSeatDTO;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;

import java.util.UUID;

public interface IBusSeatService {
    BusSeatResponse createBusSeat(BusSeatDTO busSeatDTO);
    BusSeatResponse getAllBusSeat();
    BusSeatResponse getBusSeatById(UUID id);
    BusSeatResponse getAllByIsAvailable(boolean isAvailable);
    BusSeatResponse getAllByIdBusTypes(UUID busType);
    BusSeatResponse updateBusSeat(BusSeatDTO busSeatDTO);
    BusSeatResponse deleteBusSeatById(UUID id);
}
