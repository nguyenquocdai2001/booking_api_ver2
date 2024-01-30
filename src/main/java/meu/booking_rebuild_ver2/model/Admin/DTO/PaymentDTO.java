package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private UUID id;
    private ZonedDateTime timePaid;
    private UUID idCustomer;
    private UUID idPaymentType;
    private UUID idPrice;
    private UUID idStatus;
    private UUID idUserConfig;
}
