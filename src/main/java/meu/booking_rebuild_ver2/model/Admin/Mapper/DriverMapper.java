package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.DriverDTO;
import meu.booking_rebuild_ver2.model.Admin.Driver;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;

public class DriverMapper {
    public static DriverDTO driverToDTO(Driver driver){
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setPhone(driver.getPhone());
        driverDTO.setKindOfLicense(driver.getKindOfLicense());
        driverDTO.setIdBusTypes(BusTypeMapper.busTypeDTO(driver.getIdBusTypes()));
        driverDTO.setIdUserConfig(driver.getIdUserConfig().getId());
        driverDTO.setIdStatus(driver.getStatus().getId());

        return driverDTO;
    }

    public static Driver dtoToDriver(DriverDTO driverDTO){
        Driver driver = new Driver();
        driver.setId(driverDTO.getId());
        driver.setName(driverDTO.getName());
        driver.setPhone(driverDTO.getPhone());
        driver.setKindOfLicense(driverDTO.getKindOfLicense());
        driver.setIdBusTypes(BusTypeMapper.dtoToBusTypes(driverDTO.getIdBusTypes()));
        driver.setIdUserConfig(new User(driverDTO.getIdUserConfig()));
        driver.setStatus(new Status(driverDTO.getIdStatus()));

        return driver;
    }
}
