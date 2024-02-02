package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.response.Passanger.LocationResponse;

import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
public interface ILocationService {
    LocationResponse createLocation(Location location);
    LocationResponse getAllLocations();
    LocationResponse getAllLocationsByIsDestination(boolean isDestination);
    LocationResponse getLocationById(UUID idLocation);
    LocationResponse getAllLocationsByProvince(String province);
    LocationResponse updateLocation(Location location);
    LocationResponse deleteLocationById(UUID idLocation);
}
