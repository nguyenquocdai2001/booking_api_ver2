package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BusSeatRepository extends JpaRepository<BusSeat, UUID> {
    List<BusSeat> findAllByIsAvailable(boolean isAvailable);
    List<BusSeat> findAllByIdBusTypes(BusTypes busTypes);
}
