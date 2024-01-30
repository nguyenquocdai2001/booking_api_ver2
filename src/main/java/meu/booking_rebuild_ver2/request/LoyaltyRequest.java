package meu.booking_rebuild_ver2.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class LoyaltyRequest {
    private String rank;
    private int discount;
    private double loyalty_spent;
}
