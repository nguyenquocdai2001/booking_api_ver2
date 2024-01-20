
package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time")
public class TimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private Time startTime;
    private Time endTime;
   // @JsonFormat( pattern = "dd/MM/yyyy")
    private String startDate;
   // @JsonFormat( pattern = "dd/MM/yyyy")
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "status" )
    private Status status;
    @ManyToOne
    @JoinColumn(name = "id_user_config" )
    @JsonIgnore
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}


