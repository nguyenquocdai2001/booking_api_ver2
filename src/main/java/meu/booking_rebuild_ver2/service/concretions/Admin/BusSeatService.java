package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusSeatDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.BusSeatMapper;
import meu.booking_rebuild_ver2.repository.Admin.BusSeatRepository;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.response.Admin.BusSeatResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.IBusSeatService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BusTypesRepository busTypesRepository;

    @Override
    public BusSeatResponse createBusSeat(BusSeatDTO busSeatDTO) {
        try {
            BusSeat busSeat = BusSeatMapper.dtoToBusSeat(busSeatDTO);

            BusTypes busTypes = busTypesRepository.findById(busSeatDTO.getIdBusTypes().getId()).get();
            busSeat.setIdBusTypes(busTypes);

            busSeatRepository.save(busSeat);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_ADD_SUCCESS, true, busSeatDTO);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusSeatResponse getAllBusSeat() {
        try {
            List<BusSeatDTO> busSeatListDTO = busSeatRepository.findAll()
                    .stream()
                    .map(BusSeatMapper::busSeatToDTO)
                    .toList();

            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEATS_FIND_ALL_SUCCESS, true, busSeatListDTO);
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
                BusSeatDTO busSeatDTO = BusSeatMapper.busSeatToDTO(busSeat.get());
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeatDTO);
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
                List<BusSeatDTO> busSeatListDTO = busSeatRepository.findAllByIsAvailable(isAvailable)
                        .stream()
                        .map(BusSeatMapper::busSeatToDTO)
                        .toList();

                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeatListDTO);
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
                List<BusSeatDTO> busSeatListDTO = busSeatRepository.findAllByIdBusTypes(busType)
                        .stream()
                        .map(BusSeatMapper::busSeatToDTO)
                        .toList();

                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_SUCCESS, true, busSeatListDTO);
                return response;
            }
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
            return response;
        }catch (Exception ex){
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Override
    public BusSeatResponse updateBusSeat(BusSeatDTO busSeatDTO) {
        try {
            if(!busSeatRepository.findById(busSeatDTO.getId()).isPresent()){
                BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_FIND_FAILED, false);
                return response;
            }
            BusSeat updatedBusSeat = busSeatRepository.findById(busSeatDTO.getId()).get();
            updatedBusSeat.setNameSeat(busSeatDTO.getNameSeat());
            updatedBusSeat.setFloorNumber(busSeatDTO.getFloorNumber());
            updatedBusSeat.setAvailable(busSeatDTO.isAvailable());

            BusTypes busTypes = busTypesRepository.findById(busSeatDTO.getIdBusTypes().getId()).get();
            updatedBusSeat.setIdBusTypes(busTypes);

            busSeatRepository.save(updatedBusSeat);
            BusSeatResponse response = new BusSeatResponse(Constants.MESSAGE_BUS_SEAT_UPDATE_SUCCESS, true, busSeatDTO);
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
