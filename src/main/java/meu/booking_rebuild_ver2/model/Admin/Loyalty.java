package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "loyalty")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Loyalty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private  UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(nullable = false, unique = true)
    @NotBlank
    @NotNull
    private String rank;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(nullable = false)
    @NotNull
    private int discount ;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(nullable = false)
    @NotNull
//    @OrderBy("'loyalty_spent' ASC")
    private double loyalty_spent ;
    public  Loyalty(String rank) {
        this.rank = rank;
    }

}
