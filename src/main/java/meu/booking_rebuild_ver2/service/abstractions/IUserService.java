package meu.booking_rebuild_ver2.service.abstractions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserService {
    UserDetails loadUserByUsername(String username);
    UUID getSessionUserId(HttpSession session);
    LoginResponse loginHandle(String username, String password);
    ResponseEntity<GenericResponse> registerHandle(User user);
}
