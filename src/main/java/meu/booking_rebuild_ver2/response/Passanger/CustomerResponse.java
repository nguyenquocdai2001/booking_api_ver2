package meu.booking_rebuild_ver2.response.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDto;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;

import java.util.UUID;
@Data
@NoArgsConstructor
public class CustomerResponse {
    private UUID id;
    private String name;
    private String phone;
    private LoyaltyDto loyalty;
    private String status;
    private int numberOfTrips;
    public CustomerResponse(UUID id, String name, String phone, LoyaltyDto loyalty, String status, int numberOfTrips) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.loyalty = loyalty;
        this.status = status;
        this.numberOfTrips = numberOfTrips;
    }
}
