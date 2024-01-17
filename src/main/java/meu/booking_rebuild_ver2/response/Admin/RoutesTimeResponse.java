package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesTimeResponse {
    private String message;
    private Boolean success;

    private List<RoutesTimeModel> routesTimeModelList;

    private RoutesTimeModel routesTimeModel;

    public RoutesTimeResponse(String message, Boolean success, RoutesTimeModel routesTimeModel) {
        this.message = message;
        this.success = success;
        this.routesTimeModel = routesTimeModel;
    }

    public RoutesTimeResponse(String message, Boolean success, List<RoutesTimeModel> routesTimeModelList) {
        this.message = message;
        this.success = success;
        this.routesTimeModelList = routesTimeModelList;
    }

    public RoutesTimeResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
