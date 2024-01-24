package meu.booking_rebuild_ver2.response;
import lombok.Data;
import  java.util.List;
import java.util.UUID;
@Data
public class LoginResponse {
    private String message;
    private Boolean success;
    private String token;
    private UUID id;
    private String username;
    private String fullname;
    private List<String> roles;

    public LoginResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
        this.token = null;

    }

    public LoginResponse(String message, String token, UUID id, String username,
                         String fullname, List<String> roles) {
        this.message = message;
        this.success = true;
        this.token = token;
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.roles = roles;
    }

    public LoginResponse(String message, Boolean success, String token) {
        this.message = message;
        this.success = success;
        this.token = token;
    }

    public static LoginResponse fail(String message) {
        return new LoginResponse(message, false);
    }
}
