package meu.booking_rebuild_ver2.service.concretions.Admin;

import jakarta.servlet.http.HttpServletRequest;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
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
    private UserID user;

    @Override
    public RoutesResponse createRoutes(RoutesModel routesModel) {
        if(statusRepository.existsById(routesModel.getStatus().getId())) {
           // UserID userID;
            routesModel.setIdUserConfig(user.getUserValue());
            routesRepo.save(routesModel);
            return new RoutesResponse(Constants.MESSAGE_STATUS_ADD_ROUTES_SUCCESS, true , routesModel);
        }
        return new RoutesResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);

    }

    @Override
    public RoutesResponse getAllRoutes() {
        List<RoutesModel> list = routesRepo.findAll();
        return new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
    }

    @Override
    public RoutesResponse updateRoutes(RoutesModel routesModel) {
        RoutesModel updateModel = routesRepo.findRoutesModelById(routesModel.getId());
        if(updateModel != null && statusRepository.existsById(routesModel.getStatus().getId())) {
            updateModel.setDeparturePoint(routesModel.getDeparturePoint());
            updateModel.setDestinationPoint(routesModel.getDestinationPoint());
            updateModel.setStatus(routesModel.getStatus());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(user.getUserValue());
            routesRepo.save(updateModel);
            return new RoutesResponse(Constants.MESSAGE_UPDATE_ROUTES_SUCCESS, true,updateModel);

        }
        return new RoutesResponse("ID not found", false);
    }

    @Override
    public RoutesResponse findByID(UUID id) {
        if(routesRepo.existsById(id)) {
            RoutesModel model = routesRepo.findRoutesModelById(id);
            return new RoutesResponse(Constants.MESSAGE_ROUTES_FIND_SUCCESS, true, model);
        }
        return new RoutesResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);
    }

    @Override
    public RoutesResponse deleteById(UUID id) {
        RoutesResponse response;
        if(!routesRepo.existsById(id)){
            response = new RoutesResponse("Invalid ID",true );
        }else if(!routesTimeRepository.getRoutesTimeByRoutes(id).isEmpty()){
            //check if routes_time data contain id
            response = new RoutesResponse("Route Time still has ID, can't delete",true );
        }
        else{
            routesRepo.deleteById(id);
            response = new RoutesResponse("Delete Routes Success",true);
        }
        return response;
    }

    @Override
    public RoutesResponse getRoutesByStatus(UUID id) {
        if(statusRepository.existsById(id)){
         List<RoutesModel> list = routesRepo.getRoutesByStatus(id);
            if(!list.isEmpty()){
                return new RoutesResponse(Constants.MESSAGE_STATUS_GET_ALL_ROUTES_SUCCESS, true, list);
            }
        }
        return new RoutesResponse("Status not found", false);

    }



}
