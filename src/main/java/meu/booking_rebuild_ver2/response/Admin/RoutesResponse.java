package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesResponse {
    private String message;
    private Boolean success;

    private List<RoutesDTO> routesModelList;

    private RoutesDTO routesModel;

    public RoutesResponse(String message, Boolean success, RoutesDTO routesModel) {
        this.message = message;
        this.success = success;
        this.routesModel = routesModel;
    }

    public RoutesResponse(String message, Boolean success, List<RoutesDTO> routesModelList) {
        this.message = message;
        this.success = success;
        this.routesModelList = routesModelList;
    }

    public RoutesResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
