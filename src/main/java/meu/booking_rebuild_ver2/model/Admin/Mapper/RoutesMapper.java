package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class RoutesMapper {
    public static RoutesDTO routesDTO(RoutesModel routesModel){
        RoutesDTO routesDTO = new RoutesDTO();
        routesDTO.setId(routesModel.getId());
        routesDTO.setDeparturePoint(routesModel.getDeparturePoint());
        routesDTO.setDestinationPoint(routesModel.getDestinationPoint());
        routesDTO.setIdStatus(routesModel.getStatus().getId());
        routesDTO.setIdUserConfig(routesModel.getIdUserConfig().getId());

        return routesDTO;
    }

    public static RoutesModel dtoToRoutes(RoutesDTO routesDTO){
        RoutesModel routes = new RoutesModel();
        routes.setId(routesDTO.getId());
        routes.setDeparturePoint(routesDTO.getDeparturePoint());
        routes.setDestinationPoint(routesDTO.getDestinationPoint());
        routes.setStatus(new Status(routesDTO.getIdStatus()));
        routes.setIdUserConfig(new User(routesDTO.getIdUserConfig()));
        return routes;
    }
}
