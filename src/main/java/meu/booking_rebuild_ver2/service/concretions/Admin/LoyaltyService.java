package meu.booking_rebuild_ver2.service.concretions.Admin;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoyaltyService implements ILoyaltyService {
    private static  LoyaltyRepository loyaltyRepository;
    private static  ModelMapper modelMapper;
    @Autowired
    public LoyaltyService(LoyaltyRepository loyaltyRepository){
        this.modelMapper = new ModelMapper();
        this.loyaltyRepository = loyaltyRepository;
    }
    @Override
    public Optional<Loyalty> getLoyaltyByRank(String rank) {
        Optional<Loyalty> response = loyaltyRepository.findByRank(rank.toLowerCase());
        if(response.isEmpty()){
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_GET_NOT_FOUND);
        }
        else return response;
    }

    @Override
    public GenericResponse addNewLoyalty(Loyalty request) {
        Optional<Loyalty> loylalty = loyaltyRepository.findByRank(request.getRank().toLowerCase());
        if (loylalty.isEmpty() && getLoyaltyByDiscount(request.getDiscount()).isEmpty()) {
            try {
                Loyalty loyalty = new Loyalty(request.getId(),request.getRank().toLowerCase(), request.getDiscount());
                loyaltyRepository.save(loyalty);
                GenericResponse response = new GenericResponse(Constants.MESSAGE_ADD_LOYALTY_SUCCESS);
                System.out.print(request);
                return response;
            } catch (RuntimeException e) {
                throw new GenericResponseExceptionHandler(e.getMessage());
            }
        } else if(loylalty.isPresent()){
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_RANK_FAILED);
        }
        else{
            throw new GenericResponseExceptionHandler(Constants.MESSAGE_ADD_DISCOUNT_FAILED);
        }
    }
    @Override
    public Optional<Loyalty> getLoyaltyByDiscount(int discount) {
        return loyaltyRepository.findByDiscount(discount);
    }

    @Override
    public Iterable<Loyalty> getAllLoyalty() {
        Sort sortByDiscount = Sort.by(Sort.Direction.ASC, "discount");
        return loyaltyRepository.findAll(sortByDiscount);
    }

    @Override
    public GenericResponse updateLoyalty(UUID id, LoyaltyRequest request) {
        try{
            Optional<Loyalty> model = loyaltyRepository.findById(id);
            if(model.isEmpty()) throw new GenericResponseExceptionHandler(Constants.MESSAGE_GET_NOT_FOUND);
            Loyalty loyaltyModel = model.get();
            loyaltyModel.setDiscount(request.getDiscount());
            loyaltyModel.setRank(request.getRank().toLowerCase());
            loyaltyRepository.save(loyaltyModel);
            return new GenericResponse(Constants.MESSAGE_UPDATE_LOYALTY_SUCCESS);
        }catch (RuntimeException e){
            throw new GenericResponseExceptionHandler(e.getMessage());
        }

    }
    @Override
    public GenericResponse deleteLoyalty(UUID id) {
        try{
            loyaltyRepository.deleteById(id);
            return new GenericResponse(Constants.MESSAGE_DELETED_SUCCESS);
        }catch(RuntimeException e){
            throw new GenericResponseExceptionHandler(e.getMessage());
        }
    }
}
