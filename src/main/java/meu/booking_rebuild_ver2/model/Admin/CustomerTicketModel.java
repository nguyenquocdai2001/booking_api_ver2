
package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.UUID;
/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_ticket")
public class CustomerTicketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_price" )
    private PriceModel idPrice;
    @ManyToOne
    @JoinColumn(name = "id_bus_seat" )
    private BusSeat idBusSeat;
    @ManyToOne
    @JoinColumn(name = "id_customer" )
    private Customer idCustomer;
    @ManyToOne
    @JoinColumn(name = "departure_location" )
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "destination_location" )
    private Location destinationLocation;
    @Column(name = "is_paid" )
    private boolean isPaid;
    @Column(name = "ticket_code")
    private String ticketCode;
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

    public CustomerTicketModel(UUID id) {
        this.id = id;
    }
}


