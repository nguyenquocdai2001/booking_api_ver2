package meu.booking_rebuild_ver2.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LoyaltyRequest {
    private String rank;
    private int discount;

    public LoyaltyRequest(int discount) {
        this.discount = discount;
    }

    public LoyaltyRequest(String rank) {
        this.rank = rank;
    }
}
