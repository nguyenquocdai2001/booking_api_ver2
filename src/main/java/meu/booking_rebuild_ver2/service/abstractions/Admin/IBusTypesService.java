package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusTypeDTO;
import meu.booking_rebuild_ver2.response.Admin.BusTypesResponse;

import java.util.UUID;

public interface IBusTypesService {
    BusTypesResponse createBusType(BusTypeDTO busTypeDTO);
    BusTypesResponse getAllBusTypes();
    BusTypesResponse getAllBusTypesByNumberOfSeat(int numberOfSeat);
    BusTypesResponse getAllBusTypesByStatus(UUID status);
    BusTypesResponse getBusTypesById(UUID id);
    BusTypesResponse getBusTypesByLicensePlate(String licensePlate);
    BusTypesResponse updateBusType(BusTypeDTO busTypeDTO);
    BusTypesResponse deleteBusTypesById(UUID id);
}
