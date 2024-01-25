package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BusSeatRepository extends JpaRepository<BusSeat, UUID> {
    List<BusSeat> findAllByIsAvailable(boolean isAvailable);
    @Query("SELECT bs FROM BusSeat bs WHERE bs.idBusTypes.id = :idBusType")
    List<BusSeat> findAllByIdBusTypes(UUID idBusType);
}
