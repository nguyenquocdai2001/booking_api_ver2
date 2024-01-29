package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.model.Admin.DTO.PriceDTO;
import meu.booking_rebuild_ver2.model.Admin.RoutesTimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class PriceMapper {
    public static PriceDTO priceDTO(PriceModel priceModel){
        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(priceModel.getId());
        priceDTO.setPrice(priceModel.getPrice());
        priceDTO.setIdRoutesTime(priceModel.getIdRoutesTime().getId());
        priceDTO.setIdBusType(priceModel.getIdBusType().getId());
        priceDTO.setIdStatus(priceModel.getStatus().getId());
        priceDTO.setIdUserConfig(priceModel.getIdUserConfig().getId());

        return priceDTO;
    }

    public static PriceModel dtoToPrices(PriceDTO priceDTO){
        PriceModel price = new PriceModel();
        price.setId(priceDTO.getId());
        price.setPrice(priceDTO.getPrice());
        price.setIdRoutesTime(new RoutesTimeModel(priceDTO.getIdRoutesTime()));
        price.setIdBusType(new BusTypes(priceDTO.getIdBusType()));
        price.setStatus(new Status(priceDTO.getIdStatus()));
        price.setIdUserConfig(new User(priceDTO.getIdUserConfig()));
        return price;
    }
}
