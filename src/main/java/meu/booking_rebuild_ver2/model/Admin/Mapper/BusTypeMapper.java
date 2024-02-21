package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusTypeDTO;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-9
 * */
public class BusTypeMapper {

    public static BusTypeDTO busTypeDTO(BusTypes busTypes){
        BusTypeDTO busTypeDTO = new BusTypeDTO();
        busTypeDTO.setId(busTypes.getId());
        busTypeDTO.setType(busTypes.getType());
        busTypeDTO.setLicensePlate(busTypes.getLicensePlate());
        busTypeDTO.setIdStatus(busTypes.getStatus().getId());
        busTypeDTO.setNumberOfSeat(busTypes.getNumberOfSeat());
        busTypeDTO.setIdUserConfig(busTypes.getIdUserConfig().getId());

        return busTypeDTO;
    }

    public static BusTypes dtoToBusTypes(BusTypeDTO busTypeDTO){
        BusTypes busTypes = new BusTypes();
        busTypes.setId(busTypeDTO.getId());
        busTypes.setType(busTypeDTO.getType());
        busTypes.setLicensePlate(busTypes.getLicensePlate());
        busTypes.setNumberOfSeat(busTypeDTO.getNumberOfSeat());
        busTypes.setStatus(new Status(busTypeDTO.getIdStatus()));
        busTypes.setIdUserConfig(new User(busTypeDTO.getIdUserConfig()));

        return busTypes;
    }
}
