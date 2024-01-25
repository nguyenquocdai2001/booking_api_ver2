package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesTimeRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesService;
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

    @Override
    public RoutesResponse createRoutes(RoutesModel routesModel) {
        if(statusRepository.existsById(routesModel.getStatus().getId())) {
//            routesModel.setIdUserConfig();
            routesRepo.save(routesModel);
            return new RoutesResponse(Constants.MESSAGE_STATUS_ADD_ROUTES_SUCCESS, true , routesModel);
        }
        return new RoutesResponse(Constants.MESSAGE_SOMETHING_WENT_WRONG, false);

    }

    @Override
    public List<RoutesModel> getAllRoutes() {
        return routesRepo.findAll();
    }

    @Override
    public RoutesResponse updateRoutes(RoutesModel routesModel) {
        RoutesModel updateModel = routesRepo.findRoutesModelById(routesModel.getId());
        if(updateModel != null && statusRepository.existsById(routesModel.getStatus().getId())) {
            updateModel.setDeparturePoint(routesModel.getDeparturePoint());
            updateModel.setDestinationPoint(routesModel.getDestinationPoint());
            updateModel.setStatus(routesModel.getStatus());
            updateModel.setUpdatedAt(ZonedDateTime.now());
            updateModel.setIdUserConfig(routesModel.getIdUserConfig());
            routesRepo.save(updateModel);
            return new RoutesResponse(Constants.MESSAGE_UPDATE_ROUTES_SUCCESS, true,updateModel);

        }
        return new RoutesResponse("ID not found", false);
    }

    @Override
    public RoutesModel findByID(UUID id) {
        if(routesRepo.existsById(id)){
            return routesRepo.findRoutesModelById(id);
        }
        return null;
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
    public List<RoutesModel> getRoutesByStatus(UUID id) {

        return routesRepo.getRoutesByStatus(id);
    }
}
