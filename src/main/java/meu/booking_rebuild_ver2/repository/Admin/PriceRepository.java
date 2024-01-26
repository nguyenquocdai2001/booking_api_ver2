
package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-13
 * */
@Repository
public interface PriceRepository extends JpaRepository<PriceModel, UUID> {
    PriceModel findPriceModelById(UUID id);

    @Query(value = "SELECT p FROM PriceModel p WHERE p.status.id = :statusId")
    List<PriceModel> getPriceByStatus(@Param("statusId") UUID statusId);

    @Query(value = "SELECT p FROM PriceModel p WHERE p.idBusType.id = :busTypeId")
    List<PriceModel> getPriceByBusType(@Param("busTypeId") UUID busTypeId);

    @Query(value = "SELECT p FROM PriceModel p WHERE p.idRoutesTime.id = :routesTimeId")
    List<PriceModel> getPriceByRoutesTime(@Param("routesTimeId") UUID routesTimeId);
}

