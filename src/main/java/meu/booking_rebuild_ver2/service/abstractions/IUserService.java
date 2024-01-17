package meu.booking_rebuild_ver2.service.abstractions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loadUserByUsername(String username);
    Integer getSessionUserId(HttpSession session);
    LoginResponse loginHandle(String username, String password);
}
