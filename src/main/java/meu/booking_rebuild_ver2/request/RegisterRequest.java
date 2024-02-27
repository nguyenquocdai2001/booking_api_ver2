package meu.booking_rebuild_ver2.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/*
author: nguyen minh tam
form for register
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullname;
    private String username;
    private String password;
    private String confirmPassword;
    private UUID idStatus;
}
