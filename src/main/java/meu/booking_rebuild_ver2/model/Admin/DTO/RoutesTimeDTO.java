package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesTimeDTO {
    private UUID id;
    private TimeDTO idTime;
    private RoutesDTO idRoutes;
    private UUID idStatus;
    private UUID idUserConfig;
    private UUID idBusType;
}
