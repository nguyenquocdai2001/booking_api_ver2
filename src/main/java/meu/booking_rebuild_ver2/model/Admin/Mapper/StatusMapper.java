package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import meu.booking_rebuild_ver2.model.Status;

public class StatusMapper {
    public static StatusDTO toStatusDTO(Status status){
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setStatus(status.getStatus());
        statusDTO.setFlag(status.isFlag());

        return statusDTO;
    }
}
