package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {

    Status findStatusById(UUID id);
    List<Status> findAllByFlag(boolean flag);

    @Override
    List<Status> findAll();

}
