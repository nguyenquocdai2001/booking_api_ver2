
package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.PaymentModel;
import meu.booking_rebuild_ver2.model.Admin.PaymentTypesModel;
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
public interface PaymentTypeRepository extends JpaRepository<PaymentTypesModel, UUID> {
    PaymentTypesModel findPaymentTypesModelById(UUID id);

    @Query(value = "SELECT p FROM PaymentTypesModel p WHERE p.status.id = :statusId")
    List<PaymentTypesModel> getPaymentTypesByStatus(@Param("statusId") UUID statusId);

}

