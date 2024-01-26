package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;

import java.util.UUID;
@Data
@AllArgsConstructor
public class LoyaltyDTO {
    private UUID id;
    private String rank;
    private int discount;
    private double loyalty_spent;
    private UUID idUserConfig;
    public LoyaltyDTO(Loyalty loyalty) {
        this.id = loyalty.getId();
        this.rank = loyalty.getRank();
        this.discount = loyalty.getDiscount();
        this.loyalty_spent = loyalty.getLoyaltySpent();
    }
}
