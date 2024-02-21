package meu.booking_rebuild_ver2.response;
import lombok.Data;
import meu.booking_rebuild_ver2.response.Passanger.CustomerResponse;

import  java.util.List;
import java.util.UUID;
/*
the response for login successfull
 */
@Data
public class LoginResponse {
    private String message;
    private Boolean success;
    private String token;
    private UUID id;
    private String username;
    private String fullname;
    private List<String> roles;
    private CustomerResponse customer;
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

    public LoginResponse(String message, String token, List<String> roles, CustomerResponse customer) {
        this.message = message;
        this.success = true;
        this.token = token;
        this.roles = roles;
        this.customer = customer;
    }
}
