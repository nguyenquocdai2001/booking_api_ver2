package meu.booking_rebuild_ver2.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;

@Data
@NoArgsConstructor
public class ProfileMeResponse {
    @Setter(AccessLevel.NONE)
    private String message;
    @Setter(AccessLevel.NONE)
    private boolean success;
    private UserDTO userDTO;

    public ProfileMeResponse(String message, boolean success, UserDTO userDTO) {
        this.message = message;
        this.success = success;
        this.userDTO = userDTO;
    }
}
