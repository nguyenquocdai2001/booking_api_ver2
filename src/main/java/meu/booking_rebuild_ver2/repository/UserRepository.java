package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
    @Query("select new meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO(u.id, u.fullname, u.username, u.createdAt, u.userRole) from User u where u.id =:id" )
    UserDTO getUserById(@Param("id") UUID id);
    User findUserById(UUID id);
}
