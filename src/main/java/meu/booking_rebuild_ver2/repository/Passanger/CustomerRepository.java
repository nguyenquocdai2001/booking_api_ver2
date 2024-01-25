package meu.booking_rebuild_ver2.repository.Passanger;

import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.request.Passanger.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findCustomerById(UUID id);
    Customer findCustomerByPhone(String phone);
}
