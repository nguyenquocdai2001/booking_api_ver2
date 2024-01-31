package meu.booking_rebuild_ver2.service.abstractions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.request.RegisterRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserService {
    UserDetails loadUserByUsername(String username);
    UUID getSessionUserId(HttpSession session);
    String getSessionUserName(HttpSession session);
    UserDTO getProfileMe(UUID id) throws NotFoundException;
    LoginResponse loginHandle(String username, String password);
    ResponseEntity<GenericResponse> registerHandle(RegisterRequest user);
    User getUserById(UUID id);
}
