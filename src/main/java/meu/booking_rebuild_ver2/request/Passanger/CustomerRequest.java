package meu.booking_rebuild_ver2.request.Passanger;

import jakarta.validation.constraints.Size;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.UUID;
/*
author: Nguyen  Minh Tam
form for add new customer
 */
@Data
public class CustomerRequest {
    private String name;
    private String password;
    private String phone;
    private UUID statusId;
}
