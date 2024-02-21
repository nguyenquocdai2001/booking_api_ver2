package meu.booking_rebuild_ver2.response.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Passanger.Location;

import java.util.List;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {
    private String message;
    private Boolean success;
    private List<Location> locationList;
    private Location location;

    public LocationResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
    public LocationResponse(String message, Boolean success, List<Location> locationList) {
        this.message = message;
        this.success = success;
        this.locationList = locationList;
    }
    public LocationResponse(String message, Boolean success, Location location) {
        this.message = message;
        this.success = success;
        this.location = location;
    }
}
