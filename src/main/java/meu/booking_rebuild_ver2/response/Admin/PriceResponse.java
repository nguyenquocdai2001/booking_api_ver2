
package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.PriceDTO;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
    private String message;
    private Boolean success;


    private List<PriceDTO> priceDTOList;

    private PriceDTO priceModel;

    public PriceResponse(String message, Boolean success, PriceDTO priceModel) {
        this.message = message;
        this.success = success;
        this.priceModel = priceModel;
    }

    public PriceResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public PriceResponse(String message, Boolean success, List<PriceDTO> priceDTOList) {
        this.message = message;
        this.success = success;
        this.priceDTOList = priceDTOList;
    }
}

