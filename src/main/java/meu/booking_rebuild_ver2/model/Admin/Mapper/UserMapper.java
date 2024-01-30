package meu.booking_rebuild_ver2.model.Admin.Mapper;

import jakarta.persistence.Column;
import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.request.RegisterRequest;
import meu.booking_rebuild_ver2.response.StatusResponse;
import meu.booking_rebuild_ver2.service.concretions.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private StatusService statusService;
    private static UserMapper INSTANCE;
    public static UserMapper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }
    public User toModel(UserDTO dto){
        StatusResponse status = statusService.getStatusById(dto.getIdStatus());
        User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getCreatedAt(), dto.getRole(), StatusMapper.toModel(status.getStatus()));
        return user;
    }
    public User RegisterRequestToModel (RegisterRequest request){
        StatusDTO statusDTO = statusService.getStatusById(request.getIdStatus()).getStatus();
        Status status = StatusMapper.toModel(statusDTO);
        User user = new User(request.getFullname(), request.getUsername(),request.getPassword(), request.getConfirmPassword(), status);
        return  user;

    }
}
