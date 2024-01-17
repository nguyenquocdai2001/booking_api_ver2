package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;


import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusSeatResponse {
    private String message;
    private Boolean success;

    private List<BusSeat> busSeatList;

    private Optional<BusSeat> busSeat;

    private BusSeat busSeatAdding;


    public BusSeatResponse(String message, Boolean success, List<BusSeat> busSeatList) {
        this.message = message;
        this.success = success;
        this.busSeatList = busSeatList;
    }

    public BusSeatResponse(String message, Boolean success, BusSeat busSeatAdding) {
        this.message = message;
        this.success = success;
        this.busSeatAdding = busSeatAdding;
    }

    public BusSeatResponse(String message, Boolean success, Optional<BusSeat> busSeat) {
        this.message = message;
        this.success = success;
        this.busSeat = busSeat;
    }

    public BusSeatResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
