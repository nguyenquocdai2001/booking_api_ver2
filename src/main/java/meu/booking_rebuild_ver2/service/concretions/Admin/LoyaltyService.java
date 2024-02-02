package meu.booking_rebuild_ver2.service.concretions.Admin;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Admin.Mapper.LoyaltyMapper;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.Admin.LoyaltyResponse;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
/**
 * Author: Nguyễn Minh Tâm
 * BS-2
 */
@Service
public class LoyaltyService implements ILoyaltyService {
    private static LoyaltyRepository loyaltyRepository;
    @Autowired
    private UserID userID;
    @Autowired
    private IUserService userService;
    @Autowired
    private LoyaltyMapper loyaltyMapper;
    @Autowired
    public LoyaltyService(LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
    }
    // Function to get loyalty by rank
    @Override
    public LoyaltyResponse getLoyaltyByRank(String rank) throws NotFoundException {
        Optional<Loyalty> response = loyaltyRepository.findByRank(rank.toLowerCase());
        if (response.isEmpty()) {
            throw new NotFoundException(Constants.MESSAGE_GET_LOYALTY_FAILED  + " rank : " + rank);
        } else {
            LoyaltyResponse loyaltyResponse = new LoyaltyResponse(Constants.MESSAGE_GET_SUCCESSFULL + "Get the loyalty successfully",true, loyaltyMapper.toDTO(response.get()));
            return loyaltyResponse;
        }
    }
    // Function to add new loyalty
    @Override
    public GenericResponse addNewLoyalty(Loyalty request, HttpSession httpSession) throws GenericResponseExceptionHandler {
        Optional<Loyalty> loyalty = loyaltyRepository.findByRank(request.getRank().toLowerCase());
        if (loyalty.isEmpty() && getLoyaltyByDiscount(request.getDiscount()).isEmpty()) {
            try {
                User user = userService.getUserById(userService.getSessionUserId(httpSession));
                Loyalty loyaltyModel = new Loyalty(request.getId(), request.getRank().toLowerCase(), request.getDiscount(), request.getLoyaltySpent(), user);
                loyaltyRepository.save(loyaltyModel);
                GenericResponse response = new GenericResponse(Constants.MESSAGE_ADD_LOYALTY_SUCCESS);
                System.out.print(request);
                return response;
            } catch (RuntimeException e) {
                throw new GenericResponseExceptionHandler(e.getMessage());
            }
        } else if (loyalty.isPresent()) {
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_RANK_FAILED);
        } else {
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_DISCOUNT_FAILED);
        }
    }
    // Function to get LoyaltyByDiscount to use in this service when we need to check the exits of discount
    @Override
    public Optional<Loyalty> getLoyaltyByDiscount(int discount) {

        return loyaltyRepository.findByDiscount(discount);
    }
    // The function to get all of loyalty
    @Override
    public LoyaltyResponse getAllLoyalty() {
        Sort sortByDiscount = Sort.by(Sort.Direction.ASC, "discount");
        LoyaltyResponse response = new LoyaltyResponse(Constants.MESSAGE_GET_SUCCESSFULL + "Get the list loyalty successfully",true, loyaltyRepository.findAll(sortByDiscount));
        return response;
    }
    // The function to update loyalty
    @Override
    public GenericResponse updateLoyalty(UUID id, LoyaltyRequest request, HttpSession httpSession) throws NotFoundException, GenericResponseExceptionHandler {
        try {
            Optional<Loyalty> model = loyaltyRepository.findById(id);
            if (model.isEmpty())  throw new NotFoundException("Can not get the loyalty with id:" + id);
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
    // The function to delete loyalty by id loyalty
    @Override
    public GenericResponse deleteLoyalty(UUID id) throws NotFoundException {
        try {
            Optional<Loyalty> model = loyaltyRepository.findById(id);
            if(model.isEmpty()){
                throw new NotFoundException("Can not get the loyalty with id:" + id);
            }

            loyaltyRepository.deleteById(model.get().getId());
            return new GenericResponse(Constants.MESSAGE_DELETED_SUCCESS + "The loyalty with id: " + id + " has been deleted successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    // Function to get loyalty by price
    @Override
        public LoyaltyResponse getLoyaltyByPrice(double price) throws GenericResponseExceptionHandler {
        try {
            Optional<Loyalty> model = loyaltyRepository.getLoyaltyByPrice(price);
            if (model.isEmpty()) {
                User user = userService.getUserById(userID.getUserValue().getId());
                Loyalty setLoyalty = new Loyalty(UUID.randomUUID(), " ", 0, 0, user);
                loyaltyRepository.save(setLoyalty);
                LoyaltyDTO response = new LoyaltyDTO(setLoyalty);
                LoyaltyResponse loyaltyResponse = new LoyaltyResponse(Constants.MESSAGE_GET_SUCCESSFULL + "Get the loyalty successfully. ",true,response);
                return loyaltyResponse;
            }
            LoyaltyDTO response = new LoyaltyDTO(model.get());
            LoyaltyResponse loyaltyResponse = new LoyaltyResponse(Constants.MESSAGE_GET_SUCCESSFULL + "Get the loyalty successfully.",true, response);
            return loyaltyResponse;
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
    // Function to init the price when we create new customer
    @Override
    public LoyaltyDTO getLoyaltyByPrice() throws GenericResponseExceptionHandler {
        try {
            int price = 0;
            Optional<Loyalty> model = loyaltyRepository.getLoyaltyByPrice(price);
            Loyalty loyalty = model.orElseGet(() -> {
                User user = userService.getUserById(userID.getUserValue().getId());
                Loyalty setLoyalty = new Loyalty(UUID.randomUUID(), " ", 0, 0, user);
                loyaltyRepository.save(setLoyalty);
                return setLoyalty;
            });
            return loyaltyMapper.toDTO(loyalty);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    // The function to get loyalty by id loyalty
    @Override
    public LoyaltyResponse getLoyaltyById(UUID id) throws NotFoundException {
        try{
            Optional<Loyalty> response = loyaltyRepository.findById(id);
            if(response.isPresent()){
                LoyaltyDTO loyaltyDTO = new LoyaltyDTO(response.get());
                LoyaltyResponse loyaltyResponse = new LoyaltyResponse(Constants.MESSAGE_GET_SUCCESSFULL + "Get the loyalty successfully",true, loyaltyDTO);
                return loyaltyResponse;
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
