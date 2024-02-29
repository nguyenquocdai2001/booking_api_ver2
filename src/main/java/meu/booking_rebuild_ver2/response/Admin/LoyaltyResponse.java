package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
/*
Author: Nguyen Minh Tam
Response for controller of BS-2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyResponse {
    @Setter(AccessLevel.NONE)
    private String message ;

    @Setter(AccessLevel.NONE)
    private boolean success ;

    private LoyaltyDTO loyaltyDTO;
    private Iterable<LoyaltyDTO> loyaltyDTOList;

    public LoyaltyResponse(String message, boolean success, LoyaltyDTO loyaltyDTO) {
        this.message = message;
        this.success = success;
        this.loyaltyDTO = loyaltyDTO;
    }

    public LoyaltyResponse(String message, boolean success, Iterable<LoyaltyDTO> loyaltyDTOIterable) {
        this.message = message;
        this.success = success;
        this.loyaltyDTOList = loyaltyDTOIterable;
    }
}
