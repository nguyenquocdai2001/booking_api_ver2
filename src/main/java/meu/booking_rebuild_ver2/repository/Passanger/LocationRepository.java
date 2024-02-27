package meu.booking_rebuild_ver2.repository.Passanger;

import meu.booking_rebuild_ver2.model.Passanger.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query("SELECT lc FROM Location lc WHERE lc.isDestination = :isDestination")
    List<Location> findAllByDestinationIs(boolean isDestination);
    @Query("SELECT lc FROM Location lc WHERE lc.province = :province")
    List<Location> findAllByProvince(String province);
}
