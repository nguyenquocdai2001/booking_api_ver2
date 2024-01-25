package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;

import java.util.UUID;

public interface IBusSeatService {
    BusSeatResponse createBusSeat(BusSeat busSeat);
    BusSeatResponse getAllBusSeat();
    BusSeatResponse getBusSeatById(UUID id);
    BusSeatResponse getAllByIsAvailable(boolean isAvailable);
    BusSeatResponse getAllByIdBusTypes(UUID busType);
    BusSeatResponse updateBusSeat(BusSeat busSeat);
    BusSeatResponse deleteBusSeatById(UUID id);
}
