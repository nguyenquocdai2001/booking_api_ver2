package meu.booking_rebuild_ver2.model.Admin.Mapper;

import jakarta.persistence.Column;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesBusTypeDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesBusTypeModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.stereotype.Component;

@Component
public class RoutesBusTypeMapper {
    public RoutesBusTypeDTO toDto(RoutesBusTypeModel model){
        RoutesBusTypeDTO dto = new RoutesBusTypeDTO();
        dto.setId(model.getId());
        dto.setIdBusType(model.getBusType().getId());
        dto.setIdRoute(model.getRoutesModel().getId());
        dto.setIdStatus(model.getStatus().getId());
        return dto;
    }
    public RoutesBusTypeModel toModel(RoutesBusTypeDTO dto){
        RoutesBusTypeModel model = new RoutesBusTypeModel(dto.getId()
                , new RoutesModel(dto.getIdRoute())
                , new BusTypes(dto.getIdBusType())
                , new Status(dto.getIdStatus()));
        return model;
    }
}
