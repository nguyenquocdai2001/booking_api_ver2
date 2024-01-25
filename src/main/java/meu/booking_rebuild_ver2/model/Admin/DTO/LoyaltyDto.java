package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;

import java.util.UUID;
@Data
@AllArgsConstructor
public class LoyaltyDto {
    private UUID id;
    private String rank;
    private int discount;
    private double loyalty_spent;
    public LoyaltyDto(Loyalty loyalty) {
        this.id = loyalty.getId();
        this.rank = loyalty.getRank();
        this.discount = loyalty.getDiscount();
        this.loyalty_spent = loyalty.getLoyaltySpent();
    }
}
