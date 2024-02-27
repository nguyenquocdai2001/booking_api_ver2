package meu.booking_rebuild_ver2.controller.Admin;

import lombok.extern.slf4j.Slf4j;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesBusTypeDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesBusTypeModel;
import meu.booking_rebuild_ver2.response.Admin.RoutesBusTypeResponse;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IRoutesBusTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/routes-bus-type",  produces = MediaType.APPLICATION_JSON_VALUE)
public class RoutesBusTypeController {
    @Autowired
    private IRoutesBusTypeService routesBusTypeService;
    @GetMapping("getById")
    public RoutesBusTypeResponse getRoutesBusTypeById(@RequestParam UUID id) throws NotFoundException {
        return routesBusTypeService.getRoutesBusTypeById(id);
    }
    @GetMapping("getByRoutesId")
    public RoutesBusTypeResponse getRoutesBusTypeByIdRoute(@RequestParam("idRoutes") UUID idRoutes) throws NotFoundException {
        return routesBusTypeService.getRoutesBusTypeByRoute(idRoutes);
    }

    @GetMapping("getByBusTypeId")
    public RoutesBusTypeResponse getRoutesBusTypeByIdBusType(@RequestParam UUID idBusType) throws NotFoundException {
        return routesBusTypeService.getRoutesBusTypeByBusType(idBusType);
    }
    @PostMapping("addRoutesBusType")
    public GenericResponse addRoutesBusType(@RequestBody RoutesBusTypeDTO model)  throws NotFoundException {
        return routesBusTypeService.addRoutesBusType(model);
    }
    @PutMapping("updateRoutesBusType")
    public GenericResponse updateRoutesBusType(@RequestBody RoutesBusTypeDTO model) throws NotFoundException{
        return routesBusTypeService.updateRoutesBusType(model);
    }
    @DeleteMapping("deleteRoutesBusType")
    public GenericResponse deleteRoutesBusType(@RequestParam UUID id){
        return routesBusTypeService.deleteRoutesBusType(id);
    }
}
