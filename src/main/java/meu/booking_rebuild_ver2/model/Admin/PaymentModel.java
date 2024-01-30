
package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer idCustomer;

    @ManyToOne
    @JoinColumn(name = "id_payment_type")
    private PaymentTypesModel idPaymentTypes;

    @ManyToOne
    @JoinColumn(name = "id_price")
    private PriceModel idPrice;

    private ZonedDateTime timePaid;
    @ManyToOne
    @JoinColumn(name = "status" )
    private Status status;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_user_config" )
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt = ZonedDateTime.now();;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();

    public PaymentModel(UUID id) {
        this.id = id;
    }
}


