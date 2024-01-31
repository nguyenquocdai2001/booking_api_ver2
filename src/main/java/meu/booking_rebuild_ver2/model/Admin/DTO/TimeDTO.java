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

import java.sql.Time;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {
    private UUID id;
    private Time startTime;
    private Time endTime;
    private String startDate;
    private String endDate;
    private UUID idStatus;
    private UUID idUserConfig;

    public TimeDTO(UUID id, Time startTime, Time endTime, String startDate, String endDate) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
