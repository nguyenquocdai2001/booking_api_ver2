package meu.booking_rebuild_ver2.controller.Passanger;

import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.response.Passanger.LocationResponse;
import meu.booking_rebuild_ver2.service.concretions.Passanger.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
@RestController
@RequestMapping(path = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {
    @Autowired
    LocationService locationService;

    @PostMapping(path = "/addLocation")
    public LocationResponse addLocation(@RequestBody Location location){
        return locationService.createLocation(location);
    }

    @GetMapping(path = "getAllLocations")
    public LocationResponse getAllLocations(){
        return locationService.getAllLocations();
    }

    @GetMapping(path = "getAllLocationsByIsDestination")
    public LocationResponse getAllLocationsByIsDestination(@RequestParam boolean isDestination){
        return locationService.getAllLocationsByIsDestination(isDestination);
    }

    @GetMapping(path = "getLocationById")
    public LocationResponse getLocationById(@RequestParam UUID idLocation){
        return locationService.getLocationById(idLocation);
    }

    @GetMapping(path = "getAllLocationsByProvince")
    public LocationResponse getAllLocationsByProvince(@RequestParam String province){
        return locationService.getAllLocationsByProvince(province);
    }

    @PutMapping(path = "updateLocation")
    public LocationResponse updateLocation(@RequestBody Location location){
        return locationService.updateLocation(location);
    }

    @DeleteMapping(path = "deleteLocationById")
    public LocationResponse deleteLocationById(@RequestParam UUID idLocation){
        return locationService.deleteLocationById(idLocation);
    }
}
