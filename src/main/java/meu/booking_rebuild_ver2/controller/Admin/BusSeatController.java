package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusSeatDTO;
import meu.booking_rebuild_ver2.repository.Admin.BusSeatRepository;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;
import meu.booking_rebuild_ver2.service.concretions.Admin.BusSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-8
 * */
@RestController
@RequestMapping(path = "/busSeat", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusSeatController {
    private final BusSeatRepository busSeatRepository;
    @Autowired
    private BusSeatService busSeatService;

    public BusSeatController(BusSeatRepository busSeatRepository) {
        this.busSeatRepository = busSeatRepository;
    }



    @PostMapping(path = "/addBusSeat")
    public BusSeatResponse addBusSeatPostMapping(@RequestBody @Valid BusSeatDTO busSeatDTO){
        return busSeatService.createBusSeat(busSeatDTO);
    }

    @GetMapping(path = "getAllBusSeats")
    public BusSeatResponse getAllBusSeats(){
        return busSeatService.getAllBusSeat();
    }

    @GetMapping(path = "getBusSeatById")
    public BusSeatResponse getBusSeatById(@RequestParam UUID id){
        return busSeatService.getBusSeatById(id);
    }

    @GetMapping(path = "getAllByIsAvailable")
    public BusSeatResponse getAllByIsAvailable(@RequestParam Boolean isAvailable){
        return busSeatService.getAllByIsAvailable(isAvailable);
    }

    @GetMapping(path = "getAllByIdBusTypes")
    public BusSeatResponse getAllByIdBusTypes(@RequestParam UUID idBusType){
        return busSeatService.getAllByIdBusTypes(idBusType);
    }

    @PutMapping(path = "updateBusSeat")
    public BusSeatResponse updateBusSeat(@RequestBody @Valid BusSeatDTO busSeatDTO){
        return busSeatService.updateBusSeat(busSeatDTO);
    }

    @DeleteMapping(path = "deleteBusSeatById")
    public BusSeatResponse deleteBusSeatById(@RequestParam UUID id){
        return busSeatService.deleteBusSeatById(id);
    }
}
