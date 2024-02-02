package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.stereotype.Component;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
public class StatusMapper {
    public static StatusDTO toStatusDTO(Status status){
        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setId(status.getId());
        statusDTO.setStatus(status.getStatus());
        statusDTO.setFlag(status.isFlag());

        return statusDTO;
    }
    public static Status toModel(StatusDTO statusDTO){
        Status status = new Status();
        status.setId(statusDTO.getId());
        status.setFlag(statusDTO.isFlag());
        status.setStatus(statusDTO.getStatus());
        return  status;
    }
}
