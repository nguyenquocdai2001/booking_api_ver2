package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.response.Admin.RoutesResponse;

import java.util.List;
import java.util.UUID;

public interface IRoutesService {
    RoutesResponse createRoutes(RoutesModel routesModel);
    RoutesResponse getAllRoutes();
    RoutesResponse updateRoutes(RoutesModel routesModel);
    RoutesResponse findByID(UUID id);
    RoutesResponse deleteById(UUID id);
    RoutesResponse getRoutesByStatus(UUID id);
}
