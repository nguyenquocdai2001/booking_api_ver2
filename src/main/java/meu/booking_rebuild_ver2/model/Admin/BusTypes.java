package meu.booking_rebuild_ver2.model.Admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.util.UUID;

@Entity
@Table(name = "bus_types")
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
    @ManyToOne
    @JoinColumn(name = "id_user_config")
    private User idUserConfig;

}
