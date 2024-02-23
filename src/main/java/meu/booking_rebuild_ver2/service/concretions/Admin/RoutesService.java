package meu.booking_rebuild_ver2.service.concretions.Admin;

import jakarta.servlet.http.HttpServletRequest;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.RoutesMapper;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesService;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class RoutesService implements IRoutesService{

    @Autowired
    RoutesTimeRepository routesTimeRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    RoutesRepository routesRepo;
    @Autowired
    private UserID user ;

    @Override
    public RoutesResponse createRoutes(RoutesDTO routesDTO) {
        if(statusRepository.existsById(routesDTO.getIdStatus())) {
           // UserID userID;
            routesDTO.setIdUserConfig(user.getUserValue().getId());
            RoutesModel routesModel = RoutesMapper.dtoToRoutes(routesDTO);
            Status status = statusRepository.findStatusById(routesDTO.getIdStatus());
            routesModel.setStatus(status);
            routesRepo.save(routesModel);
            return new RoutesResponse(Constants.MESSAGE_STATUS_ADD_ROUTES_SUCCESS, true , routesDTO);
        }
        return new RoutesResponse("Status " + Constants.MESSAGE_ID_NOT_FOUND, false);

    }

    @Override
    public RoutesResponse getAllRoutes() {
        List<RoutesDTO> list = routesRepo.findAll()
                .stream()
                .map(RoutesMapper::routesDTO)
                .toList();
        return new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
    }

    @Override
    public RoutesResponse updateRoutes(RoutesDTO routesModel) {
        RoutesModel updateModel = routesRepo.findRoutesModelById(routesModel.getId());
        if(updateModel != null && statusRepository.existsById(routesModel.getIdStatus())) {
            updateModel.setDeparturePoint(routesModel.getDeparturePoint());
            updateModel.setDestinationPoint(routesModel.getDestinationPoint());
            Status status = statusRepository.findStatusById(routesModel.getIdStatus());
            updateModel.setStatus(status);
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(user.getUserValue());
            routesRepo.save(updateModel);
            return new RoutesResponse(Constants.MESSAGE_UPDATE_ROUTES_SUCCESS, true,routesModel);

        }
        return new RoutesResponse(Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public RoutesResponse findByID(UUID id) {
        if(routesRepo.existsById(id)) {
            RoutesModel model = routesRepo.findRoutesModelById(id);
            RoutesDTO routesDTO = RoutesMapper.routesDTO(model);
            return new RoutesResponse(Constants.MESSAGE_ROUTES_FIND_SUCCESS, true, routesDTO);
        }
        return new RoutesResponse("Route " + Constants.MESSAGE_ID_NOT_FOUND, false);
    }

    @Override
    public RoutesResponse deleteById(UUID id) {
        RoutesResponse response;
        if(!routesRepo.existsById(id)){
            response = new RoutesResponse(Constants.MESSAGE_ID_NOT_FOUND,true );
        }else if(!routesTimeRepository.getRoutesTimeByRoutes(id).isEmpty()){
            //check if routes_time data contain id
            response = new RoutesResponse(Constants.MESSAGE_ROUTES_TIME_STILL_HAS,true );
        }
        else{
            routesRepo.deleteById(id);
            response = new RoutesResponse("Route "+ Constants.MESSAGE_DELETED_SUCCESS,true);
        }
        return response;
    }

    @Override
    public RoutesResponse getRoutesByStatus(UUID id) {
        if(statusRepository.existsById(id)){
         List<RoutesDTO> list = routesRepo.getRoutesByStatus(id)
                 .stream()
                 .map(RoutesMapper::routesDTO)
                 .toList();
            if(!list.isEmpty()){
                return new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
            }
        }
        return new RoutesResponse("Status "+ Constants.MESSAGE_ID_NOT_FOUND, false);

    }



}
