package meu.booking_rebuild_ver2.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/*
author: nguyen minh tam
form for login
 */
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private String password;

    @Email
    @Pattern(regexp = "(^[0-9A-Za-z][\\w.\\-]+@[\\w]+\\.[\\w]\\S+\\w)$", message = "Invalid email!")
    @NotBlank
    private String email;
    @NotBlank
    private String phone;

}
