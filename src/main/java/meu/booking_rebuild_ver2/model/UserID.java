package meu.booking_rebuild_ver2.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserID {
    private UUID userID;

    public User getUserValue() {
        User user = new User() ;
        user.setId(userID);
        return user;
    }

    public void setUserValue(UUID userID) {
        this.userID = userID;
    }
}
