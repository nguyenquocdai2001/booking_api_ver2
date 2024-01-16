package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimeRepository extends JpaRepository<TimeModel, UUID> {
}
