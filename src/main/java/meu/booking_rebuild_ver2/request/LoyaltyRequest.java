package meu.booking_rebuild_ver2.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
author: nguyen minh tam
form for add new loyalty
 */
@Data
@NoArgsConstructor
public class LoyaltyRequest {
    private String rank;
    private int discount;
    private double loyalty_spent;
}
