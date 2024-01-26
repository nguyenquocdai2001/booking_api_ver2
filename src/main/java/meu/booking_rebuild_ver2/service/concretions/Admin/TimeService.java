package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class TimeService implements ITimeService {
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    RoutesTimeRepository routesTimeRepository;
    @Autowired
    StatusRepository statusRepository;
    @Override
    public TimeResponse createTime(TimeModel timeModel) {
        /* checkTimeInput start*/
        TimeResponse response;
        if(checkDate(timeModel)){
            response  = new TimeResponse("Invalid day",false, timeModel);
            return response;
        }
        /* checkTimeInput end*/
        if(statusRepository.existsById(timeModel.getStatus().getId())){
            timeRepository.save(timeModel);
            response = new TimeResponse(Constants.MESSAGE_STATUS_ADD_TIME_SUCCESS, true, timeModel);
            return response;
        }
        return new TimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public TimeResponse getAllTime() {
        List<TimeModel> list = timeRepository.findAll();
        return new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);

    }

    @Override
    public TimeResponse updateTime(TimeModel timeModel) {
        TimeModel updateModel = timeRepository.findTimeModelById(timeModel.getId());
        TimeResponse response;
        if(checkDate(timeModel)){
            response  = new TimeResponse("Invalid day",false, timeModel);
            return response;
        }
        if(updateModel != null && statusRepository.existsById(timeModel.getStatus().getId())) {
            updateModel.setStartTime(timeModel.getStartTime());
            updateModel.setEndTime(timeModel.getEndTime());
            updateModel.setStartDate(timeModel.getStartDate());
            updateModel.setEndDate(timeModel.getEndDate());
            updateModel.setStatus(timeModel.getStatus());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(timeModel.getIdUserConfig());
            timeRepository.save(updateModel);
            response = new TimeResponse(Constants.MESSAGE_UPDATE_TIME_SUCCESS, true, updateModel);
            return response;
        }
        response = new TimeResponse("ID not found", false);
        return response;
    }

    @Override
    public TimeResponse findByID(UUID id) {
        TimeResponse response;
        if(timeRepository.existsById(id)) {
            TimeModel model = timeRepository.findTimeModelById(id);
            response = new TimeResponse(Constants.MESSAGE_TIME_FIND_SUCCESS, true, model);
            return response;
        }
        response = new TimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        return response;

    }

    @Override
    public TimeResponse deleteById(UUID id) {
        TimeResponse response;
        if(!timeRepository.existsById(id)){
            response = new TimeResponse("Invalid ID",true );
            return response;
        }else if(!routesTimeRepository.getRoutesTimeByTime(id).isEmpty()){
            //check if routes_time data contain id
            response = new TimeResponse("Route Time still has ID, can't delete",true );
            return response;
        }
        else{
            timeRepository.deleteById(id);
            response = new TimeResponse("Delete Time Success",true );
            return response;
        }

    }

    @Override
    public TimeResponse getTimeByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<TimeModel> list = timeRepository.getTimeByStatus(id);
            TimeResponse response;
            if (!list.isEmpty()) {
                response = new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
                return response;
            }
            return new TimeResponse("List is empty", false);
        }
        return new TimeResponse("Status not found", false);
    }
    private boolean checkDate(TimeModel timeModel){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate specificDateStart = LocalDate.parse(timeModel.getStartDate(),formatter);
        LocalDate specificDateEnd = LocalDate.parse(timeModel.getEndDate(),formatter);
        return currentDate.isAfter(specificDateStart)
                || currentDate.isAfter(specificDateEnd)
                || specificDateStart.isAfter(specificDateEnd);
    }

}
