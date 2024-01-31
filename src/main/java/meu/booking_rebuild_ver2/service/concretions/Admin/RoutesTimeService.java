package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesTimeDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.RoutesTimeMapper;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesTimeResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RoutesTimeService implements IRoutesTimeService{

    @Autowired
    RoutesTimeRepository routesTimeRepo;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    RoutesRepository routesRepo;
    @Autowired
    TimeRepository timeRepository;
    @Autowired
    private UserID user ;

    @Override
    public RoutesTimeResponse createRoutesTime(RoutesTimeDTO routesTimeDTO) {
        try {
            RoutesTimeResponse response;
            if(!checkIdTimeAndRoute(routesTimeDTO)){
                response = new RoutesTimeResponse("Time or Route or Status is Invalid", false , routesTimeDTO);
            }else {
                routesTimeDTO.setIdUserConfig(user.getUserValue().getId());
                RoutesTimeModel model = RoutesTimeMapper.dtoToRoutesTimes(routesTimeDTO);
                routesTimeRepo.save(model);
                response = new RoutesTimeResponse(Constants.MESSAGE_STATUS_ADD_ROUTE_TIME_SUCCESS, true, routesTimeDTO);
            }
            return response;
        }catch(Exception e){
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public RoutesTimeResponse getAllRoutesTime() {
        List<RoutesTimeDTO> list = routesTimeRepo.findAll()
                .stream()
                .map(RoutesTimeMapper::routesTimeDTO)
                .toList();
        return new RoutesTimeResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
    }

    @Override
    public RoutesTimeResponse updateRoutesTime(RoutesTimeDTO routesTimeDTO) {
        RoutesTimeModel updatemodel = routesTimeRepo.findRoutesTimeModelById(routesTimeDTO.getId());
        if(updatemodel != null && checkIdTimeAndRoute(routesTimeDTO)) {
            updatemodel.setIdRoutes(new RoutesModel(routesTimeDTO.getIdRoutes().getId()));
            updatemodel.setIdTime(new TimeModel(routesTimeDTO.getIdTime().getId()));
            updatemodel.setStatus(new Status(routesTimeDTO.getIdStatus()));
            updatemodel.setUpdatedAt(ZonedDateTime.now());
            updatemodel.setIdUserConfig(user.getUserValue());
            routesTimeRepo.save(updatemodel);
            return new RoutesTimeResponse(Constants.MESSAGE_UPDATE_ROUTES_SUCCESS, true,routesTimeDTO);

        }
        return new RoutesTimeResponse("ID not found", false);
    }

    @Override
    public RoutesTimeResponse findByID(UUID id) {
        if(routesTimeRepo.existsById(id)) {
            RoutesTimeModel model = routesTimeRepo.findRoutesTimeModelById(id);
            RoutesTimeDTO modelDTO = RoutesTimeMapper.routesTimeDTO(model);
            return new RoutesTimeResponse(Constants.MESSAGE_ROUTES_FIND_SUCCESS, true, modelDTO);
        }
        return new RoutesTimeResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public RoutesTimeResponse deleteById(UUID id) {
        RoutesTimeResponse response;
        if(!routesTimeRepo.existsById(id)){
            response = new RoutesTimeResponse("Invalid ID",true );
        }
        else{
            routesTimeRepo.deleteById(id);
            response = new RoutesTimeResponse("Delete RoutesTime Success",true);
        }
        return response;
    }

    @Override
    public RoutesTimeResponse getRoutesTimeByStatus(UUID id) {
        if(statusRepository.existsById(id)){
         List<RoutesTimeDTO> list = routesTimeRepo.getRoutesTimeByStatus(id)
                 .stream()
                 .map(RoutesTimeMapper::routesTimeDTO)
                 .toList();
            if(!list.isEmpty()){
                return new RoutesTimeResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
            }
        }
        return new RoutesTimeResponse("Status not found", false);

    }

    @Override
    public RoutesTimeResponse getRoutesTimeByTime(UUID id) {
        try{
            RoutesTimeResponse response;
            if(!timeRepository.existsById(id)){
                response = new RoutesTimeResponse("Invalid ID", true);
            }else{
                List<RoutesTimeDTO> routesTimeDTOList = routesTimeRepo.getRoutesTimeByTime(id)
                        .stream()
                        .map(RoutesTimeMapper::routesTimeDTO).toList();
                response = new RoutesTimeResponse("Get routes by time success",true ,routesTimeDTOList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RoutesTimeResponse getRoutesTimeByRoutes(UUID id) {
        try{
            RoutesTimeResponse response;
            if(!routesRepo.existsById(id)){
                response = new RoutesTimeResponse("Invalid ID", true);
            }else{
                List<RoutesTimeDTO> routesTimeList = routesTimeRepo.getRoutesTimeByRoutes(id)
                        .stream()
                        .map(RoutesTimeMapper::routesTimeDTO)
                        .toList();
                response = new RoutesTimeResponse("Get routes by routes success",true ,routesTimeList);
            }
            return response;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    private boolean checkIdTimeAndRoute(RoutesTimeDTO model){
        return timeRepository.existsById(model.getIdTime().getId())
                && routesRepo.existsById(model.getIdRoutes().getId())
                && statusRepository.existsById(model.getIdStatus());
    }

}
