package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesBusTypeDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesBusTypeModel;
import meu.booking_rebuild_ver2.response.Admin.RoutesBusTypeResponse;
import meu.booking_rebuild_ver2.response.GenericResponse;

import java.util.UUID;

public interface IRoutesBusTypeService {
    RoutesBusTypeResponse getRoutesBusTypeById(UUID id) throws NotFoundException;
    RoutesBusTypeResponse getRoutesBusTypeByRoute(UUID idRoutes) throws NotFoundException;
    RoutesBusTypeResponse getRoutesBusTypeByBusType(UUID idBusType) throws NotFoundException;
    GenericResponse addRoutesBusType(RoutesBusTypeDTO model)  throws NotFoundException ;
    GenericResponse updateRoutesBusType(RoutesBusTypeDTO model) throws NotFoundException;
    GenericResponse deleteRoutesBusType(UUID id);
}
