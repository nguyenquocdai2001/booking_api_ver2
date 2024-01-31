package meu.booking_rebuild_ver2.model;

import meu.booking_rebuild_ver2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserID {
    private UUID userID;
    @Autowired
    private UserRepository userRepo;

    public User getUserValue() {
        return userRepo.findById(this.userID).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        // userRepo.findUserByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    public void setUserValue(UUID userID) {
        this.userID = userID;
    }
}
