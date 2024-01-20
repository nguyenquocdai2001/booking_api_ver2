package meu.booking_rebuild_ver2.controller.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.repository.Admin.BusSeatRepository;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/busSeat", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusSeatController {
    private final BusSeatRepository busSeatRepository;

    public BusSeatController(BusSeatRepository busSeatRepository) {
        this.busSeatRepository = busSeatRepository;
    }

    @PostMapping(path = "/addBusSeat")
    public BusSeatResponse addBusSeatPostMapping(@RequestBody @Valid BusSeat busSeat){
        try {
            busSeatRepository.save(busSeat);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_ADD_SUCCESS, true, busSeat);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllBusSeats")
    public BusSeatResponse getAllBusSeats(){
        try {
            List<BusSeat> busSeatList = busSeatRepository.findAll();
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEATS_FIND_ALL_SUCCESS, true, busSeatList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getBusSeatById")
    public BusSeatResponse getBusSeatById(UUID id){
        try {
            if(busSeatRepository.findById(id).isPresent()){
                Optional<BusSeat> busSeat = busSeatRepository.findById(id);
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeat);
                return response;
            }
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllByIsAvailable")
    public BusSeatResponse getAllByIsAvailable(Boolean isAvailable){
        try {
            if(!(busSeatRepository.findAllByIsAvailable(isAvailable).isEmpty())){
                List<BusSeat> busSeatList = busSeatRepository.findAllByIsAvailable(isAvailable);
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeatList);
                return response;
            }
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "getAllByIdBusTypes")
    public BusSeatResponse getAllByIdBusTypes(BusTypes busType){
        try {
            if(!(busSeatRepository.findAllByIdBusTypes(busType).isEmpty())){
                List<BusSeat> busSeatList = busSeatRepository.findAllByIdBusTypes(busType);
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeatList);
                return response;
            }
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "updateBusSeat")
    public BusSeatResponse updateBusSeat(@RequestBody @Valid BusSeat busSeat){
        try {
            if(!busSeatRepository.findById(busSeat.getId()).isPresent()){
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
                return response;
            }
            BusSeat updatedBusSeat = busSeatRepository.findById(busSeat.getId()).get();
            updatedBusSeat.setNameSeat(busSeat.getNameSeat());
            updatedBusSeat.setFloorNumber(busSeat.getFloorNumber());
            updatedBusSeat.setAvailable(busSeat.isAvailable());
            updatedBusSeat.setIdBusTypes(busSeat.getIdBusTypes());
            busSeatRepository.save(updatedBusSeat);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_UPDATE_SUCCESS, true, updatedBusSeat);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @DeleteMapping(path = "deleteBusSeatById")
    public BusSeatResponse deleteBusSeatById(@RequestBody @Valid BusSeat busSeat){
        try {
            if(busSeatRepository.findById(busSeat.getId()) == null){
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
                return response;
            }
            busSeatRepository.deleteById(busSeat.getId());
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_DELETE_BUS_SEAT_SUCCESS, true, busSeat);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
