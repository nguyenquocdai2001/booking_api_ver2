package meu.booking_rebuild_ver2.response.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDto;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID id;
    private String name;
    private String phone;
    private LoyaltyDto loyalty;
    private String status;
    private int numberOfTrips;
}
