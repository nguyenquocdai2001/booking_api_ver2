package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface BusTypesRepository extends JpaRepository<BusTypes, UUID> {
    List<BusTypes> findALlByNumberOfSeat(int numberOfSeat);
    List<BusTypes> findAllByStatus(Status status);

    Optional<BusTypes> findById(UUID id);
    BusTypes findByLicensePlate(String licensePlate);

}
