package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypesDTO {
    private UUID id;
    private String paymentType;
    private UUID idStatus;
    private UUID idUserConfig;
}
