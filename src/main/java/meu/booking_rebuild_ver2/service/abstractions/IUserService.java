package meu.booking_rebuild_ver2.service.abstractions;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails loadUserByUsername(String username);
}
