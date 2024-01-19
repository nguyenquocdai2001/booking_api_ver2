
package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "price")
public class PriceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   // @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    private String price;
    

    @ManyToOne
    @JoinColumn(name = "status" )
    private Status status;
    @ManyToOne
    @JoinColumn(name = "id_user_config" )
    @JsonIgnore
    private User idUserConfig;
    @JsonIgnore
    private ZonedDateTime createdAt;
    @JsonIgnore
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}


