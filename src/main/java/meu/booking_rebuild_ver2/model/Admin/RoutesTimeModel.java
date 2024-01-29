package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "routes_time")
public class RoutesTimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_time")
    private TimeModel idTime;
    @ManyToOne
    @JoinColumn(name = "id_route")
    private RoutesModel idRoutes;

    @ManyToOne
    @JoinColumn(name = "status" )
    private Status status;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_user_config" )
    @JsonIgnore
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public RoutesTimeModel(UUID id) {
        this.id = id;
    }
}
