package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Setter;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyResponse {
    @Setter(AccessLevel.NONE)
    private String message = "The request has been blocked";

    @Setter(AccessLevel.NONE)
    private boolean success = false;

    private LoyaltyDTO loyaltyDTO;
    private Iterable<LoyaltyDTO> loyaltyDTOList;

    public LoyaltyResponse(LoyaltyDTO loyaltyDTO) {
        this.message = Constants.MESSAGE_GET_SUCCESSFULL;
        this.success = true;
        this.loyaltyDTO = loyaltyDTO;
    }

    public LoyaltyResponse(Iterable<LoyaltyDTO> loyaltyDTOIterable) {
        this.message = Constants.MESSAGE_GET_SUCCESSFULL;
        this.success = true;
        this.loyaltyDTOList = loyaltyDTOIterable;
    }
}
