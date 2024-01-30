package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class LoyaltyMapper {
    private final IUserService userService;
    @Autowired
    public LoyaltyMapper(IUserService userService) {
        this.userService = userService;
    }
    public  Loyalty toModel(LoyaltyDTO dto){
        Loyalty loyalty = new Loyalty();
        User user = userService.getUserById(dto.getIdUserConfig());
        loyalty.setId(dto.getId());
        loyalty.setRank(dto.getRank());
        dto.setDiscount(dto.getDiscount());
        dto.setLoyalty_spent(dto.getLoyalty_spent());
        loyalty.setUserConfig(user);
        return loyalty;
    }
    public  LoyaltyDTO toDTO(Loyalty loyalty){
        LoyaltyDTO loyaltyDTO = new LoyaltyDTO();
        loyaltyDTO.setId(loyalty.getId());
        loyaltyDTO.setRank(loyalty.getRank());
        loyaltyDTO.setLoyalty_spent(loyalty.getLoyaltySpent());
        loyaltyDTO.setIdUserConfig(loyalty.getUserConfig().getId());
        return loyaltyDTO;
    }
}
