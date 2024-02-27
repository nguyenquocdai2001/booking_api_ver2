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

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-9
 * */
@Entity
@Table(name = "bus_types")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    @Column(name = "license_plate")
    private String licensePlate;
    @Column(name = "number_of_seat")
    private int numberOfSeat;
    @ManyToOne
    @JoinColumn(name = "status")
    private Status status;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_user_config")
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public BusTypes(UUID id) {
        this.id = id;
    }
}
