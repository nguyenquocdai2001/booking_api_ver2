package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.sql.Time;
import java.util.TimeZone;
import java.time.ZonedDateTime;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "time")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start_date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    private Time start_time;
    private Time end_time;
    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    @OneToOne
    @JoinColumn(name = "id_user_config")
    private User id_user_config;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at = ZonedDateTime.now();
}
