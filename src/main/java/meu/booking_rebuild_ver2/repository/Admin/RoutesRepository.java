package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoutesRepository extends JpaRepository<RoutesModel, UUID> {
    RoutesModel findRoutesModelById(UUID id);

    @Query(value = "SELECT T FROM RoutesModel T WHERE T.status.id = :statusId")
    List<RoutesModel> getRoutesByStatus(@Param("statusId") UUID statusId);
}
