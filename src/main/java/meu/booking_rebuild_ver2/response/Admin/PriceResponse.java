
package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
    private String message;
    private Boolean success;

    private List<PriceModel> priceModelList;

    private PriceModel priceModel;

    public PriceResponse(String message, Boolean success, PriceModel priceModel) {
        this.message = message;
        this.success = success;
        this.priceModel = priceModel;
    }

    public PriceResponse(String message, Boolean success, List<PriceModel> priceModelList) {
        this.message = message;
        this.success = success;
        this.priceModelList = priceModelList;
    }

    public PriceResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}

