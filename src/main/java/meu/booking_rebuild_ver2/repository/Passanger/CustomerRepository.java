package meu.booking_rebuild_ver2.repository.Passanger;

import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findCustomerById(UUID id);
    Customer findCustomerByPhone(String phone);
//    @Query("select c from Customer c where c.loyalty.id = :loyaltyId")
    List<Customer> getCustomersByLoyalty_Id(@Param("loyaltyId") UUID loyaltyId);
    @Query("SELECT c FROM Customer c WHERE c.phone LIKE %:phone%")
    List<Customer> getCustomersByPhone(String phone);
    @Query("SELECT NEW meu.booking_rebuild_ver2.response.Passanger.CustomerResponse(c.id, c.name, c.phone, c.loyalty.id, c.status.id, c.numberOfTrips) " +
            "FROM Customer c WHERE c.phone LIKE %:phone%")
    Page<CustomerResponse> getCustomersByPhoneAsPage(String phone, Pageable pageable);
    @Query("SELECT NEW meu.booking_rebuild_ver2.response.Passanger.CustomerResponse(c.id, c.name, c.phone, c.loyalty.id, c.status.id, c.numberOfTrips) " +
            "FROM Customer c WHERE c.loyalty.id = :loyaltyId")
    Page<CustomerResponse> getCustomersByLoyaltyAsPage(@Param("loyaltyId") UUID loyaltyId, Pageable pageable);
}
