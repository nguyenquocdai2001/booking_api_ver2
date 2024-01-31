package meu.booking_rebuild_ver2.model.Admin.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
public class RoutesDTO {
    private UUID id;
    private String departurePoint;
    private String destinationPoint;
    private UUID idStatus;
    private UUID idUserConfig;

    public RoutesDTO(UUID id, String departurePoint, String destinationPoint) {
        this.id = id;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
    }
}
