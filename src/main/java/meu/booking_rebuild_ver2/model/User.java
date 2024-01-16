package meu.booking_rebuild_ver2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "admin")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 100)
    @Size(max = 100)
    @Pattern(regexp = "^[ a-zA-Z0-9_.'\\-]+?", message = "Invalid characters in name")
    @NotBlank
    private String fullname;
    @Column(nullable = false, unique = true)
    @Email
    @Pattern(regexp = "(^[0-9A-Za-z][\\w.\\-]+@gmail.com)", message = "Invalid email!")
    @NotBlank
    private String username;
    @Column(length = 100)
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 8)
    private String confirmPass;
    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider = AuthProvider.LOCAL ;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private UserRole userRole ;
    public String getUserRole() {
        return String.valueOf(userRole);
    }
}
