package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoyaltyRepository extends CrudRepository<Loyalty, UUID> {
    Optional<Loyalty> findByRank(String rank);
    Optional<Loyalty> findByDiscount(int discount);
    Optional<Loyalty> findById(UUID id);
    @Query("SELECT NEW meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO(l.id, l.rank, l.discount, l.loyaltySpent, l.UserConfig.id) from Loyalty l")
    Iterable<LoyaltyDTO> findAll(Sort sort);
    @Query("SELECT l FROM Loyalty l WHERE l.loyaltySpent <= :loyaltySpent ORDER BY l.loyaltySpent DESC LIMIT 1")
    Optional<Loyalty> getLoyaltyByPrice(double loyaltySpent);
}
