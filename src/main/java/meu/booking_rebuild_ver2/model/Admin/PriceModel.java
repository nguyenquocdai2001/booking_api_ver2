
package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price")
public class PriceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String price;

    @ManyToOne
    @JoinColumn(name = "id_bus_type")
    private BusTypes idBusType;

    @ManyToOne
    @JoinColumn(name = "id_routes_time")
    private RoutesTimeModel idRoutesTime;
    @ManyToOne
    @JoinColumn(name = "status" )
    private Status status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_config" )
    @JsonIgnore
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}


