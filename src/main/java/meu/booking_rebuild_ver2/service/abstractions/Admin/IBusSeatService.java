package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;

import java.util.UUID;

public interface IBusSeatService {
    BusSeatResponse createBusSeat(BusSeat busSeat);
    public BusSeatResponse getAllBusSeat();
    public BusSeatResponse getBusSeatById(UUID id);
    public BusSeatResponse getAllByIsAvailable(boolean isAvailable);
    public BusSeatResponse getAllByIdBusTypes(UUID busType);
    public BusSeatResponse updateBusSeat(BusSeat busSeat);
    public BusSeatResponse deleteBusSeatById(UUID id);
}
