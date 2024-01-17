package meu.booking_rebuild_ver2.repository.Admin;

import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.response.Admin.TimeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-13
 * */
@Repository
public interface TimeRepository extends JpaRepository<TimeModel, UUID> {
    TimeModel findTimeModelById(UUID id);

    @Query(value = "SELECT T FROM TimeModel T WHERE T.status.id = :statusId")
    List<TimeModel> getTimeByStatus(@Param("statusId") UUID statusId);
}
