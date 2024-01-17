package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoutesTimeRepository extends JpaRepository<RoutesTimeModel, UUID> {
    RoutesTimeModel findRoutesTimeModelById(UUID id);

    @Query(value = "SELECT T FROM RoutesTimeModel T WHERE T.status.id = :statusId")
    List<RoutesTimeModel> getRoutesTimeByStatus(@Param("statusId") UUID statusId);

    @Query(value = "SELECT T FROM RoutesTimeModel T WHERE T.idTime.id = :timeId")
    List<RoutesTimeModel> getRoutesTimeByTime(@Param("timeId") UUID timeId);

    @Query(value = "SELECT T FROM RoutesTimeModel T WHERE T.idRoutes.id = :routesId")
    List<RoutesTimeModel> getRoutesTimeByRoutes(@Param("routesId") UUID routesId);
}
