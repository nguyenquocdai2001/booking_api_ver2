package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.*;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesTimeDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class RoutesTimeMapper {
    public static RoutesTimeDTO routesTimeDTO(RoutesTimeModel routesTimeModel){
        RoutesTimeDTO routesTimeDTO = new RoutesTimeDTO();
        routesTimeDTO.setId(routesTimeModel.getId());
        routesTimeDTO.setIdRoutes(new RoutesDTO(routesTimeModel.getIdRoutes().getId()
                ,routesTimeModel.getIdRoutes().getDeparturePoint(),routesTimeModel.getIdRoutes().getDestinationPoint()));
        routesTimeDTO.setIdTime(new TimeDTO(routesTimeModel.getIdTime().getId(),
                routesTimeModel.getIdTime().getStartTime(),
                routesTimeModel.getIdTime().getEndTime(),
                routesTimeModel.getIdTime().getStartDate(),
                routesTimeModel.getIdTime().getEndDate()));
        routesTimeDTO.setIdStatus(routesTimeModel.getStatus().getId());
        routesTimeDTO.setIdUserConfig(routesTimeModel.getIdUserConfig().getId());
        routesTimeDTO.setIdBusType(routesTimeModel.getIdBusType().getId());
        return routesTimeDTO;
    }

    public static RoutesTimeModel dtoToRoutesTimes(RoutesTimeDTO routesTimeDTO){
        RoutesTimeModel routesTime = new RoutesTimeModel();
        routesTime.setId(routesTimeDTO.getId());
        routesTime.setIdRoutes(new RoutesModel(routesTimeDTO.getIdRoutes().getId()));
        routesTime.setIdTime(new TimeModel(routesTimeDTO.getIdTime().getId()));
        routesTime.setStatus(new Status(routesTimeDTO.getIdStatus()));
        routesTime.setIdUserConfig(new User(routesTimeDTO.getIdUserConfig()));
        routesTime.setIdBusType(new BusTypes(routesTimeDTO.getIdBusType()));
        return routesTime;
    }
}
