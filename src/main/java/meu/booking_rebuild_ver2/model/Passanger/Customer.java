package meu.booking_rebuild_ver2.model.Passanger;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Status;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 100)
    private String name;
    @Size(max = 10, min = 10)
    @Column(nullable = false, unique = true)
    private String phone;
    @Column(name = "number_of_trip")
    private int numberOfTrips;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonBackReference
    @JoinColumn(name = "status")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_loyalty")
    @JsonBackReference
    @JsonIgnore
    private Loyalty loyalty;

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Customer(UUID id) {
        this.id = id;
    }
}
