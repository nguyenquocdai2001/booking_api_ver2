package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesTimeDTO;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesTimeResponse {
    private String message;
    private Boolean success;

    private List<RoutesTimeDTO> routesTimeDTOList;

    private RoutesTimeDTO routesTimeDTO;

    public RoutesTimeResponse(String message, Boolean success, RoutesTimeDTO routesTimeDTO) {
        this.message = message;
        this.success = success;
        this.routesTimeDTO = routesTimeDTO;
    }

    public RoutesTimeResponse(String message, Boolean success, List<RoutesTimeDTO> routesTimeDTOList) {
        this.message = message;
        this.success = success;
        this.routesTimeDTOList = routesTimeDTOList;
    }

    public RoutesTimeResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
