package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.RoutesBusTypeDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesBusTypeResponse {
    private String message;
    private boolean success;
    private RoutesBusTypeDTO routesBusTypeDTO;
}
