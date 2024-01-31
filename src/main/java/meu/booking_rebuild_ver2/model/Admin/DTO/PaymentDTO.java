package meu.booking_rebuild_ver2.model.Admin.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private UUID id;
    @JsonFormat( pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timePaid;
    private UUID idCustomer;
    private UUID idPaymentType;
    private UUID idPrice;
    private UUID idStatus;
    private UUID idUserConfig;
}
