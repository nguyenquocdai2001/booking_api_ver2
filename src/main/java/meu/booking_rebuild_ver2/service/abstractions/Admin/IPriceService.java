package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.response.Admin.PriceResponse;

import java.util.List;
import java.util.UUID;

public interface IPriceService {
    PriceResponse createPrice(PriceModel priceModel);
    List<PriceModel> getAllPrice();
    PriceResponse updatePrice(PriceModel priceModel);
    PriceModel findByID(UUID id);
    PriceResponse deleteById(UUID id);
    List<PriceModel> getPriceByStatus(UUID id);
//    List<PriceModel> getPriceByBusType(UUID id);
//    List<PriceModel> getPriceByRoutesTime(UUID id);
}
