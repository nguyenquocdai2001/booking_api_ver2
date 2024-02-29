package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesBusTypeDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.RoutesBusTypeMapper;
import meu.booking_rebuild_ver2.model.Admin.RoutesBusTypeModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesBusTypeRepository;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.response.Admin.RoutesBusTypeResponse;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesBusTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoutesBusTypesService implements IRoutesBusTypeService {
    @Autowired
    private RoutesBusTypeRepository repo;
    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private BusTypesRepository busTypesRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private RoutesBusTypeMapper routesBusTypeMapper;
    @Override
    public RoutesBusTypeResponse getRoutesBusTypeById(UUID id)throws NotFoundException {
        try{
            RoutesBusTypeModel model = repo.findRoutesBusTypeModelById(id);
            if(model == null){
                throw new NotFoundException("Can not the RoutesBusType with id" + id);
            }
            RoutesBusTypeDTO modelDTO = routesBusTypeMapper.toDto(model);
            RoutesBusTypeResponse response = new RoutesBusTypeResponse(Constants.MESSAGE_GET_SUCCESSFULL, true, modelDTO);
            return response;
        }catch (RuntimeException e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RoutesBusTypeResponse getRoutesBusTypeByRoute(UUID idRoutes) throws NotFoundException {
        try{
            RoutesBusTypeModel model = repo.findRoutesBusTypeModelByRoutesModel(idRoutes);
            if(model == null){
                throw new NotFoundException("Can not the RoutesBusType with id" + idRoutes);
            }
            RoutesBusTypeDTO modelDTO = routesBusTypeMapper.toDto(model);
            RoutesBusTypeResponse response = new RoutesBusTypeResponse(Constants.MESSAGE_GET_SUCCESSFULL, true, modelDTO);
            return response;
        }catch (RuntimeException e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public RoutesBusTypeResponse getRoutesBusTypeByBusType(UUID idBusType) throws NotFoundException {
        try{
            RoutesBusTypeModel model = repo.findRoutesBusTypeModelByBusType(idBusType);
            if(model == null){
                throw new NotFoundException("Can not the RoutesBusType with id" + idBusType);
            }
            RoutesBusTypeDTO modelDTO = routesBusTypeMapper.toDto(model);
            RoutesBusTypeResponse response = new RoutesBusTypeResponse(Constants.MESSAGE_GET_SUCCESSFULL, true, modelDTO);
            return response;
        }catch (RuntimeException e)
        {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public GenericResponse addRoutesBusType(RoutesBusTypeDTO model) throws NotFoundException {
        try{
            RoutesModel routesModel = routesRepository.findRoutesModelById(model.getIdRoute());
            if(routesModel == null){
                throw new NotFoundException("Can not get the route with id = " + model.getIdRoute());
            }
            Optional<BusTypes> busTypes = busTypesRepository.findById(model.getIdBusType());
            if(busTypes.isEmpty()){
                throw new NotFoundException("Can not get the bus type with id = " + model.getIdBusType());
            }
            Status status = statusRepository.findStatusById(model.getIdStatus());
            if(status == null){
                throw new NotFoundException("Can not get the status with id = " + model.getIdStatus());
            }
            RoutesBusTypeModel modelToSave = new RoutesBusTypeModel(routesModel, busTypes.get(), status);
            repo.save(modelToSave);
            return new GenericResponse("Added Routes Bus Type", true);
        }
        catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public GenericResponse updateRoutesBusType(RoutesBusTypeDTO model) throws NotFoundException {
        try{
            RoutesModel routesModel = routesRepository.findRoutesModelById(model.getIdRoute());
            if(routesModel == null){
                throw new NotFoundException("Can not get the route with id = " + model.getIdRoute());
            }
            Optional<BusTypes> busTypes = busTypesRepository.findById(model.getIdBusType());
            if(busTypes.isEmpty()){
                throw new NotFoundException("Can not get the bus type with id = " + model.getIdBusType());
            }
            Status status = statusRepository.findStatusById(model.getIdStatus());
            if(status == null){
                throw new NotFoundException("Can not get the status with id = " + model.getIdStatus());
            }
            RoutesBusTypeModel modelUpdate = repo.findRoutesBusTypeModelById(model.getId());
            if(modelUpdate == null){
                throw new NotFoundException("Can not get the routes bus type with id = " + model.getId());
            }
            modelUpdate.setStatus(status);
            modelUpdate.setRoutesModel(routesModel);
            modelUpdate.setBusType(busTypes.get());
            repo.save(modelUpdate);
            return new GenericResponse("Item has been updated! ", true);
        }
        catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public GenericResponse deleteRoutesBusType(UUID id) {
        try{
            RoutesBusTypeModel model = repo.findRoutesBusTypeModelById(id);
            repo.delete(model);
            return new GenericResponse("Item has been deleted", true);
        } catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
