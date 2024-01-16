package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTypesResponse {
    private String message;
    private Boolean success;

    private List<BusTypes> busTypesList;


    private Optional<BusTypes> busTypes;

    private BusTypes busTypes1;

    public BusTypesResponse(String message, Boolean success, Optional<BusTypes> busTypes) {
        this.message = message;
        this.success = success;
        this.busTypes = busTypes;
    }

    public BusTypesResponse(String message, Boolean success, BusTypes busTypes1) {
        this.message = message;
        this.success = success;
        this.busTypes1 = busTypes1;
    }

    public BusTypesResponse(String message, Boolean success, List<BusTypes> busTypesList) {
        this.message = message;
        this.success = success;
        this.busTypesList = busTypesList;
    }

    public BusTypesResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
