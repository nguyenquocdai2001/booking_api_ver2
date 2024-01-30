package meu.booking_rebuild_ver2.model.Admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.util.UUID;

@Entity
@Table(name = "driver")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "kind_of_license")
    private String kindOfLicense;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_bus_Type")
    private BusTypes idBusTypes;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_user_config")
    private User idUserConfig;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "status")
    private Status status;

}
