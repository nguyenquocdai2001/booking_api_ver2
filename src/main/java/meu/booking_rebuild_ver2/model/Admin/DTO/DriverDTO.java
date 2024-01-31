package meu.booking_rebuild_ver2.model.Admin.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private UUID id;
    private String name;
    private String phone;
    private String kindOfLicense;
    private UUID idBusTypes;
    private UUID idUserConfig;
    private UUID idStatus;
}
