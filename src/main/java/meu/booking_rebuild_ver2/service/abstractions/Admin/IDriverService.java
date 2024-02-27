package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.DriverDTO;
import meu.booking_rebuild_ver2.response.Admin.DriverResponse;

import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-11
 * */
public interface IDriverService {
    DriverResponse createDriver(DriverDTO driverDTO);
    DriverResponse getAllDriver();
    DriverResponse getAllByIdBusTypes(UUID idBusType);
    DriverResponse getByPhone(String phone);
    DriverResponse getByKindOfLicense(String kindOfLicense);
    DriverResponse getDriverById(UUID idDriver);
    DriverResponse updateDriver(DriverDTO driverDTO);
    DriverResponse deleteDriverById(UUID idDriver);
}
