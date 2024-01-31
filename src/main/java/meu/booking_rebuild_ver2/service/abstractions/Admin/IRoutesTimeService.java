package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesTimeDTO;
import meu.booking_rebuild_ver2.response.Admin.RoutesTimeResponse;

import java.util.UUID;

public interface IRoutesTimeService {
    RoutesTimeResponse createRoutesTime(RoutesTimeDTO routesDTO);
    RoutesTimeResponse getAllRoutesTime();
    RoutesTimeResponse updateRoutesTime(RoutesTimeDTO routesDTO);
    RoutesTimeResponse findByID(UUID id);
    RoutesTimeResponse deleteById(UUID id);
    RoutesTimeResponse getRoutesTimeByStatus(UUID id);
    RoutesTimeResponse getRoutesTimeByTime(UUID id);
    RoutesTimeResponse getRoutesTimeByRoutes(UUID id);
}
