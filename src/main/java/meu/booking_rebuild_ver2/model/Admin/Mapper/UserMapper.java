package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.User;

public class UserMapper {
    private static UserMapper INSTANCE;
    public static UserMapper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UserMapper();
        }
        return INSTANCE;
    }
    public User toModel(UserDTO dto){
        User user = new User(dto.getId(), dto.getName(), dto.getUsername(), dto.getCreatedAt(), dto.getRole());
        return user;
    }
}
