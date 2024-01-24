package meu.booking_rebuild_ver2.request.Passanger;

import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class CustomerRequest {
    private String name;
    private String phone;
}
