package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.model.Admin.DTO.DriverDTO;
import meu.booking_rebuild_ver2.response.Admin.DriverResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/driver", produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping(path = "/addDriver")
    public DriverResponse addDriver(@RequestBody @Valid DriverDTO driverDTO){
        return driverService.createDriver(driverDTO);
    }

    @GetMapping(path = "getAllDrivers")
    public DriverResponse getAllDrivers(){
        return driverService.getAllDriver();
    }

    @GetMapping(path = "getAllDriversByIdBusTypes")
    public DriverResponse getAllByIdBusTypes(UUID idBusTypes){
        return driverService.getAllByIdBusTypes(idBusTypes);
    }

    @GetMapping(path = "getDriverByPhone")
    public DriverResponse getByPhone(String phone){
        return driverService.getByPhone(phone);
    }

    @GetMapping(path = "getDriverByKindOfLicense")
    public DriverResponse getByKindOfLicense(String kindOfLicense){
        return driverService.getByKindOfLicense(kindOfLicense);
    }

    @PostMapping(path = "updateDriver")
    public DriverResponse updateDriver(@RequestBody @Valid DriverDTO driverDTO){
        return driverService.updateDriver(driverDTO);
    }

    @DeleteMapping(path = "deleteDriverById")
    public DriverResponse deleteDriverById(UUID idDriver){
        return driverService.deleteDriverById(idDriver);
    }
}
