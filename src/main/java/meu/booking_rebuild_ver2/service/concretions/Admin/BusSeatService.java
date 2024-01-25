package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.repository.Admin.BusSeatRepository;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IBusSeatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusSeatService implements IBusSeatService {
    private final BusSeatRepository busSeatRepository;

    public BusSeatService(BusSeatRepository busSeatRepository) {
        this.busSeatRepository = busSeatRepository;
    }

    @Override
    public BusSeatResponse createBusSeat(BusSeat busSeat) {
        try {
            busSeatRepository.save(busSeat);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_ADD_SUCCESS, true, busSeat);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusSeatResponse getAllBusSeat() {
        try {
            List<BusSeat> busSeatList = busSeatRepository.findAll();
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEATS_FIND_ALL_SUCCESS, true, busSeatList);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusSeatResponse getBusSeatById(UUID id) {
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

    @Override
    public BusSeatResponse getAllByIsAvailable(boolean isAvailable) {
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

    @Override
    public BusSeatResponse getAllByIdBusTypes(UUID busType) {
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

    @Override
    public BusSeatResponse updateBusSeat(BusSeat busSeat) {
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

    @Override
    public BusSeatResponse deleteBusSeatById(UUID id) {
        try {
            if(busSeatRepository.findById(id) == null){
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
                return response;
            }
            busSeatRepository.deleteById(id);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_DELETE_BUS_SEAT_SUCCESS, true);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }
}
