package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class TimeMapper {
    public static TimeDTO timeDTO(TimeModel timeModel){
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setId(timeModel.getId());
        timeDTO.setStartTime(timeModel.getStartTime());
        timeDTO.setEndTime(timeModel.getEndTime());
        timeDTO.setStartDate(timeModel.getStartDate());
        timeDTO.setEndDate(timeModel.getEndDate());
        timeDTO.setIdStatus(timeModel.getStatus().getId());
        timeDTO.setIdUserConfig(timeModel.getIdUserConfig().getId());

        return timeDTO;
    }

    public static TimeModel dtoToTimes(TimeDTO timeDTO){
        TimeModel time = new TimeModel();
        time.setId(timeDTO.getId());
        time.setStartDate(timeDTO.getStartDate());
        time.setEndDate(timeDTO.getEndDate());
        time.setStartTime(timeDTO.getStartTime());
        time.setEndTime(timeDTO.getEndTime());
        time.setStatus(new Status(timeDTO.getIdStatus()));
        time.setIdUserConfig(new User(timeDTO.getIdUserConfig()));
        return time;
    }
}
