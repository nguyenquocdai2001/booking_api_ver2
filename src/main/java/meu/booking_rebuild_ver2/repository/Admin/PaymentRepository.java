
package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.PaymentModel;
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
public interface PaymentRepository extends JpaRepository<PaymentModel, UUID> {
    PaymentModel findPaymentModelById(UUID id);

    @Query(value = "SELECT p FROM PaymentModel p WHERE p.status.id = :statusId")
    List<PaymentModel> getPaymentByStatus(@Param("statusId") UUID statusId);

    @Query(value = "SELECT p FROM PaymentModel p WHERE p.idCustomer.id = :customerId")
    List<PaymentModel> getPaymentByCustomer(@Param("customerId") UUID customerId);

    @Query(value = "SELECT p FROM PaymentModel p WHERE p.idPaymentTypes.id = :paymentTypeId")
    List<PaymentModel> getPaymentByPaymentTypes(@Param("paymentTypeId") UUID paymentTypeId);
    @Query(value = "SELECT p FROM PaymentModel p WHERE p.idPrice.id = :priceId")
    List<PaymentModel> getPaymentByPrice(@Param("priceId") UUID priceId);
}

