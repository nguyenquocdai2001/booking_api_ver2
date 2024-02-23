package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.TimeMapper;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
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
    @Autowired
    private UserID userID;
    @Override
    public TimeResponse createTime(TimeDTO timeDTO) {
        /* checkTimeInput start*/
        TimeResponse response;
        if(checkDate(timeDTO)){
            response  = new TimeResponse("Date or Time is: " + Constants.MESSAGE_INVALID_DATA,false);
            return response;
        }
        /* checkTimeInput end*/
        if(statusRepository.existsById(timeDTO.getIdStatus())){
            timeDTO.setIdUserConfig(userID.getUserValue().getId());
            TimeModel timeModel = TimeMapper.dtoToTimes(timeDTO);
            Status status = statusRepository.findStatusById(timeDTO.getIdStatus());
            timeModel.setStatus(status);
            timeRepository.save(timeModel);
            response = new TimeResponse(Constants.MESSAGE_STATUS_ADD_TIME_SUCCESS, true, timeDTO);
            return response;
        }
        return new TimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public TimeResponse getAllTime() {
        List<TimeDTO> list = timeRepository.findAll()
                .stream()
                .map(TimeMapper::timeDTO)
                .toList();
        return new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);

    }

    @Override
    public TimeResponse updateTime(TimeDTO timeDTO) {
        TimeModel updateModel = timeRepository.findTimeModelById(timeDTO.getId());
        TimeResponse response;
        if(checkDate(timeDTO)){
            response  = new TimeResponse("Date or Time is: " + Constants.MESSAGE_INVALID_DATA,false);
            return response;
        }
        if(updateModel != null && statusRepository.existsById(timeDTO.getIdStatus())) {
            updateModel.setStartTime(timeDTO.getStartTime());
            updateModel.setEndTime(timeDTO.getEndTime());
            updateModel.setStartDate(timeDTO.getStartDate());
            updateModel.setEndDate(timeDTO.getEndDate());
            Status status = statusRepository.findStatusById(timeDTO.getIdStatus());
            updateModel.setStatus(status);
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(userID.getUserValue());
            timeRepository.save(updateModel);
            response = new TimeResponse(Constants.MESSAGE_UPDATE_TIME_SUCCESS, true, timeDTO);
            return response;
        }
        response = new TimeResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
        return response;
    }

    @Override
    public TimeResponse findByID(UUID id) {
        TimeResponse response;
        if(timeRepository.existsById(id)) {
            TimeModel model = timeRepository.findTimeModelById(id);
            TimeDTO timeDTO = TimeMapper.timeDTO(model);
            response = new TimeResponse(Constants.MESSAGE_TIME_FIND_SUCCESS, true, timeDTO);
            return response;
        }
        response = new TimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
        return response;

    }

    @Override
    public TimeResponse deleteById(UUID id) {
        TimeResponse response;
        if(!timeRepository.existsById(id)){
            response = new TimeResponse(Constants.MESSAGE_ID_NOT_FOUND,false );
            return response;
        }else if(!routesTimeRepository.getRoutesTimeByTime(id).isEmpty()){
            //check if routes_time data contain id
            response = new TimeResponse(Constants.MESSAGE_ROUTES_TIME_STILL_HAS,false );
            return response;
        }
        else{
            timeRepository.deleteById(id);
            response = new TimeResponse("Time " +Constants.MESSAGE_DELETE_SUCCESS,true );
            return response;
        }

    }

    @Override
    public TimeResponse getTimeByStatus(UUID id) {
        if(statusRepository.existsById(id)) {
            List<TimeDTO> list = timeRepository.getTimeByStatus(id)
                    .stream()
                    .map(TimeMapper::timeDTO)
                    .toList();
            TimeResponse response;
            if (!list.isEmpty()) {
                response = new TimeResponse(Constants.MESSAGE_STATUS_GET_ALL_TIME_SUCCESS, true, list);
                return response;
            }
            return new TimeResponse(Constants.MESSAGE_EMPTY_LIST, false);
        }
        return new TimeResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
    }
    private boolean checkDate(TimeDTO timeModel){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate specificDateStart = LocalDate.parse(timeModel.getStartDate(),formatter);
        LocalDate specificDateEnd = LocalDate.parse(timeModel.getEndDate(),formatter);
        return currentDate.isAfter(specificDateStart)
                || currentDate.isAfter(specificDateEnd)
                || specificDateStart.isAfter(specificDateEnd);
    }

}
