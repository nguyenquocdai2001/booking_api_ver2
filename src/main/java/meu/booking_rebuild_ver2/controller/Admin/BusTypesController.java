package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import meu.booking_rebuild_ver2.response.Admin.BusTypesResponse;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.BusTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/busTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusTypesController {
    private final BusTypesRepository busTypesRepository;
    @Autowired
    private BusTypesService busTypesService;

    public BusTypesController(BusTypesRepository busTypesRepository) {
        this.busTypesRepository = busTypesRepository;
    }

    @PostMapping(path = "/addBusTypes")
    public BusTypesResponse addBusTypesPostMapping(@RequestBody @Valid BusTypes busTypes){
        return busTypesService.createBusType(busTypes);
    }

    @GetMapping(path = "getAllBusTypes")
    public BusTypesResponse getAllBusTypes(){
        return busTypesService.getAllBusTypes();
    }

    @GetMapping(path = "getAllBusTypesByNumberOfSeat")
    public BusTypesResponse getAllBusTypesByNumberOfSeat(@RequestParam int numberOfSeat){
        return busTypesService.getAllBusTypesByNumberOfSeat(numberOfSeat);
    }

    @GetMapping(path = "getAllBusTypesByStatus")
    public BusTypesResponse getAllBusTypesByStatus(@RequestParam UUID idStatus){
        return busTypesService.getAllBusTypesByStatus(idStatus);
    }

    @GetMapping(path = "getBusTypesById")
    public BusTypesResponse getBusTypesById(@RequestParam UUID id){
        return busTypesService.getBusTypesById(id);
    }

    @GetMapping(path = "getBusTypesByLicensePlate")
    public BusTypesResponse getBusTypesByLicensePlate(@RequestParam String licensePlate){
        return busTypesService.getBusTypesByLicensePlate(licensePlate);
    }

    @PostMapping(path = "updateBusType")
    public BusTypesResponse updateBusType(@RequestBody @Valid BusTypes busTypes){
        return busTypesService.updateBusType(busTypes);
    }

    @DeleteMapping(path = "deleteBusTypesById")
    public BusTypesResponse deleteBusTypesById(@RequestParam UUID id){
        return busTypesService.deleteBusTypesById(id);
    }
}
