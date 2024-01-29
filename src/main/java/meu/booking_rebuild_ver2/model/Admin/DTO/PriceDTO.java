package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDTO {
    private UUID id;
    private String price;
    private UUID idBusType;
    private UUID idRoutesTime;
    private UUID idStatus;
    private UUID idUserConfig;
}
