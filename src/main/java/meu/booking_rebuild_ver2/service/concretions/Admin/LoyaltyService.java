package meu.booking_rebuild_ver2.service.concretions.Admin;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Admin.Mapper.UserMapper;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service

public class LoyaltyService implements ILoyaltyService {
    private static LoyaltyRepository loyaltyRepository;
    private static ModelMapper modelMapper;
    @Autowired
    private UserID userID;
    @Autowired
    private IUserService userService;
    @Autowired
    public LoyaltyService(LoyaltyRepository loyaltyRepository) {
        this.modelMapper = new ModelMapper();
        this.loyaltyRepository = loyaltyRepository;
    }

    @Override
    public Optional<Loyalty> getLoyaltyByRank(String rank) throws NotFoundException {
        Optional<Loyalty> response = loyaltyRepository.findByRank(rank.toLowerCase());
        if (response.isEmpty()) {
            throw new NotFoundException(Constants.MESSAGE_GET_LOYALTY_FAILED  + " rank : " + rank);
        } else return response;
    }
    @Override
    public GenericResponse addNewLoyalty(Loyalty request) throws GenericResponseExceptionHandler {
        Optional<Loyalty> loylalty = loyaltyRepository.findByRank(request.getRank().toLowerCase());
        if (loylalty.isEmpty() && getLoyaltyByDiscount(request.getDiscount()).isEmpty()) {
            try {
                Loyalty loyalty = new Loyalty(request.getId(), request.getRank().toLowerCase(), request.getDiscount(), request.getLoyaltySpent(), userID.getUserValue());
                loyaltyRepository.save(loyalty);
                GenericResponse response = new GenericResponse(Constants.MESSAGE_ADD_LOYALTY_SUCCESS);
                System.out.print(request);
                return response;
            } catch (RuntimeException e) {
                throw new GenericResponseExceptionHandler(e.getMessage());
            }
        } else if (loylalty.isPresent()) {
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_RANK_FAILED);
        } else {
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_DISCOUNT_FAILED);
        }
    }

    @Override
    public Optional<Loyalty> getLoyaltyByDiscount(int discount) {

        return loyaltyRepository.findByDiscount(discount);
    }

    @Override
    public Iterable<LoyaltyDTO> getAllLoyalty() {
        Sort sortByDiscount = Sort.by(Sort.Direction.ASC, "discount");
        return loyaltyRepository.findAll(sortByDiscount);
    }
    @Override
    public GenericResponse updateLoyalty(UUID id, LoyaltyRequest request, HttpSession httpSession) throws NotFoundException, GenericResponseExceptionHandler {
        try {
            Optional<Loyalty> model = loyaltyRepository.findById(id);
            if (model.isEmpty())  throw new NotFoundException();
            Loyalty loyaltyModel = model.get();
            loyaltyModel.setDiscount(request.getDiscount());
            loyaltyModel.setLoyaltySpent(request.getLoyalty_spent());
            loyaltyModel.setRank(request.getRank().toLowerCase());
            User user = userService.getUserById(userService.getSessionUserId(httpSession));
            loyaltyModel.setUserConfig(user);
            loyaltyRepository.save(loyaltyModel);
            return new GenericResponse(Constants.MESSAGE_UPDATE_LOYALTY_SUCCESS);
        } catch (RuntimeException e) {
            throw new GenericResponseExceptionHandler(e.getMessage());
        }
    }
    @Override
    public GenericResponse deleteLoyalty(UUID id) throws NotFoundException {
        try {
            Optional<Loyalty> model = loyaltyRepository.findById(id);
            if(model.isEmpty()){
                throw new NotFoundException();
            }
            loyaltyRepository.deleteById(model.get().getId());
            return new GenericResponse(Constants.MESSAGE_DELETED_SUCCESS);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
        public LoyaltyDTO getLoyaltyByPrice(double price) throws GenericResponseExceptionHandler {
        try {
            Optional<Loyalty> model = loyaltyRepository.getLoyaltyByPrice(price);
            if (model.isEmpty()) {
                User user = userService.getUserById(userID.getUserValue().getId());
                Loyalty setLoyalty = new Loyalty(UUID.randomUUID(), "economy", 0, 0, user);
                loyaltyRepository.save(setLoyalty);
                LoyaltyDTO response = new LoyaltyDTO(setLoyalty);
                return response;
            }
            LoyaltyDTO response = new LoyaltyDTO(model.get());
            return response;
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Override
    public Loyalty getLoyaltyByPrice() throws GenericResponseExceptionHandler {
        try {
            int price = 0;
            Optional<Loyalty> model = loyaltyRepository.getLoyaltyByPrice(price);
            if (model.isEmpty()) {
                User user = userService.getUserById(userID.getUserValue().getId());
                Loyalty setLoyalty = new Loyalty(UUID.randomUUID(), "economy", 0, 0,user);
                loyaltyRepository.save(setLoyalty);
                return setLoyalty;
            }
            Loyalty response = model.get();
            return response;
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    @Override
    public LoyaltyDTO getLoyaltyById(UUID id) throws NotFoundException {
        try{
            Optional<Loyalty> response = loyaltyRepository.findById(id);
            if(response.isPresent()){
                LoyaltyDTO loyaltyDTO = new LoyaltyDTO(response.get());
                return loyaltyDTO;
            }
            else{
                throw new NotFoundException(Constants.MESSAGE_GET_LOYALTY_FAILED + "id = " + id);
            }
        }
       catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
