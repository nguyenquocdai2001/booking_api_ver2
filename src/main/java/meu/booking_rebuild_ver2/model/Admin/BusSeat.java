package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "bus_seat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "is_available")
    private boolean isAvailable;
    @Column(name = "name_seat")
    private String nameSeat;
    @Column(name = "floor_number")
    private int floorNumber;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_bus_type")
    private BusTypes idBusTypes;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}
