package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoyaltyRepository extends CrudRepository<Loyalty, UUID> {
    Optional<Loyalty> findByRank(String rank);
    Optional<Loyalty> findByDiscount(int discount);
    Optional<Loyalty> findById(UUID id);
    Iterable<Loyalty> findAll(Sort sort);
}
