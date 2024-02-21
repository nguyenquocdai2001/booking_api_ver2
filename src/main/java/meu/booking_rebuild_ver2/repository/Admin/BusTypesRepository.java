package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-9
 * */
@Repository
public interface BusTypesRepository extends JpaRepository<BusTypes, UUID> {
    List<BusTypes> findALlByNumberOfSeat(int numberOfSeat);
    @Query("SELECT bt FROM BusTypes bt WHERE bt.status.id = :idStatus")
    List<BusTypes> findAllByStatus(UUID idStatus);

    Optional<BusTypes> findById(UUID id);
    BusTypes findByLicensePlate(String licensePlate);

}
