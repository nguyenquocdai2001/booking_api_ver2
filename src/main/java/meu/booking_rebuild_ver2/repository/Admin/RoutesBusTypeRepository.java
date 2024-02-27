package meu.booking_rebuild_ver2.repository.Admin;


import meu.booking_rebuild_ver2.model.Admin.RoutesBusTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoutesBusTypeRepository extends JpaRepository<RoutesBusTypeModel, UUID> {
    RoutesBusTypeModel findRoutesBusTypeModelById (UUID id);
    @Query("SELECT r from RoutesBusTypeModel r where r.busType.id = :idBusType ")
    RoutesBusTypeModel findRoutesBusTypeModelByBusType(@Param("idBusType")UUID idBusType);
    @Query("SELECT r from RoutesBusTypeModel r where r.routesModel.id = :idRoutes ")
    RoutesBusTypeModel findRoutesBusTypeModelByRoutesModel(@Param("idRoutes") UUID idRoutes);
}
