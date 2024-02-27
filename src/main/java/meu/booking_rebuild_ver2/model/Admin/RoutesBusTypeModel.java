package meu.booking_rebuild_ver2.model.Admin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;

import java.util.UUID;

@Table(name = "routes_bus_type")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoutesBusTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_route")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private RoutesModel routesModel;
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_bus_type")
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BusTypes busType;
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "status", nullable = false)
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignore Hibernate properties to avoid serialization issues
    private Status status;

    public RoutesBusTypeModel(UUID id) {
        this.id = id;
    }

    public RoutesBusTypeModel(RoutesModel routesModel, BusTypes busType, Status status) {
        this.routesModel = routesModel;
        this.busType = busType;
        this.status = status;
    }
}

