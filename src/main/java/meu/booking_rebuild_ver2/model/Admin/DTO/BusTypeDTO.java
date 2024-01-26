package meu.booking_rebuild_ver2.model.Admin.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTypeDTO {
    private UUID id;
    private String type;

    private String licensePlate;

    private int numberOfSeat;

    private UUID idStatus;

    private UUID idUserConfig;
}
