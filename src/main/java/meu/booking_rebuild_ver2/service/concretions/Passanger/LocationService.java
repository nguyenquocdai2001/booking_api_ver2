package meu.booking_rebuild_ver2.service.concretions.Passanger;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.repository.Passanger.LocationRepository;
import meu.booking_rebuild_ver2.response.Passanger.LocationResponse;
import meu.booking_rebuild_ver2.service.abstractions.Passanger.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
@Service
public class LocationService implements ILocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public LocationResponse createLocation(Location location) {
        try {
            Location locationSaved = locationRepository.save(location);
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_ADD_SUCCESS, true, locationSaved);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse getAllLocations() {
        try {
            List<Location> locationList = locationRepository.findAll();
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_ALL_SUCCESS, true, locationList);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse getAllLocationsByIsDestination(boolean isDestination) {
        try {
            if(locationRepository.findAllByDestinationIs(isDestination).isEmpty()){
                LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_NULL, false);
                return response;
            }
            List<Location> locationList = locationRepository.findAllByDestinationIs(isDestination);
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_LOCATION_SUCCESS, true, locationList);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse getLocationById(UUID idLocation) {
        try {
            if(!locationRepository.existsById(idLocation)){
                LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_NULL, false);
                return response;
            }
            Location location = locationRepository.findById(idLocation).get();
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_LOCATION_SUCCESS, true, location);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse getAllLocationsByProvince(String province) {
        try {
            if(locationRepository.findAllByProvince(province).isEmpty()){
                LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_NULL, false);
                return response;
            }
            List<Location> locationList = locationRepository.findAllByProvince(province);
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_LOCATION_SUCCESS, true, locationList);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse updateLocation(Location location) {
        try {
            if(!locationRepository.existsById(location.getId())){
                LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_NULL, false);
                return response;
            }
            Location locationUpdated = locationRepository.findById(location.getId()).get();
            locationUpdated.setHouseNumber(location.getHouseNumber());
            locationUpdated.setStreet(location.getStreet());
            locationUpdated.setWard(location.getWard());
            locationUpdated.setDistrict(location.getDistrict());
            locationUpdated.setProvince(location.getProvince());
            locationUpdated.setDestination(location.isDestination());
            locationUpdated.setUpdatedAt(ZonedDateTime.now());
            locationRepository.save(locationUpdated);

            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_UPDATE_SUCCESS, true, locationUpdated);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public LocationResponse deleteLocationById(UUID idLocation) {
        try {
            if(!locationRepository.existsById(idLocation)){
                LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_GET_NULL, false);
                return response;
            }
            locationRepository.deleteById(idLocation);
            LocationResponse response = new LocationResponse(Constants.MESSAGE_LOCATION_DELETE_SUCCESS, true);
            return response;
        }catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
