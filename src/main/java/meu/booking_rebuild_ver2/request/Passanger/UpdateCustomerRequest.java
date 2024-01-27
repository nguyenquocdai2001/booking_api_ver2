package meu.booking_rebuild_ver2.request.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Setter
@AllArgsConstructor
public class UpdateCustomerRequest {
    @NotNull
    private String name;
    @NotNull
    @NotBlank
    private String phone;
    @NotNull
    @NotBlank
    private int numberOfTrips;
    @NotNull
    @NotBlank
    private String status;
    private double totalPaid;
    private LoyaltyDTO loyaltyDto;

}
