package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ITimeService {
    TimeResponse createTime(TimeDTO timeModel);
    TimeResponse getAllTime();
    TimeResponse updateTime(TimeDTO timeModel);
    TimeResponse findByID(UUID id);
    TimeResponse deleteById(UUID id);
    TimeResponse getTimeByStatus(UUID id);
}
