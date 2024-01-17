package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesResponse {
    private String message;
    private Boolean success;

    private List<RoutesModel> routesModelList;

    private RoutesModel routesModel;

    public RoutesResponse(String message, Boolean success, RoutesModel routesModel) {
        this.message = message;
        this.success = success;
        this.routesModel = routesModel;
    }

    public RoutesResponse(String message, Boolean success, List<RoutesModel> routesModelList) {
        this.message = message;
        this.success = success;
        this.routesModelList = routesModelList;
    }

    public RoutesResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
