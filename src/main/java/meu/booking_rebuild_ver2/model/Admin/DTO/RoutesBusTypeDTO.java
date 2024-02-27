package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesBusTypeDTO {
    private UUID id;
    private UUID idRoute;
    private UUID idBusType;
    private UUID idStatus;

    public RoutesBusTypeDTO(UUID idRoute, UUID idBusType, UUID idStatus) {
        this.idRoute = idRoute;
        this.idBusType = idBusType;
        this.idStatus = idStatus;
    }
}
