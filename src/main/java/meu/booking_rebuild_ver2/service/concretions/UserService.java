package meu.booking_rebuild_ver2.service.concretions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.Admin.Mapper.StatusMapper;
import meu.booking_rebuild_ver2.model.Admin.Mapper.UserMapper;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.request.RegisterRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.service.abstractions.IStatusService;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.impl.UserDetailsImplement;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
author: Nguyen Minh Tam
service: The service to using for manage about authorize of admin
 */
@Service
public class UserService implements IUserService {
    public static final String USERID = "USER_ID";
    public static final String USEREMAIL = "USER_EMAIL";
    private static final String EMAIL_REGEX =
            "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    public static final String USER_EMAIL = "USER_EMAIL";
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private IStatusService statusService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtils jwtUtils) {
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }
    // Function to get id by http session
    @Override
    public UUID getSessionUserId(@NotNull HttpSession session) {

        return (UUID) session.getAttribute(USERID);
    }
    // Function to get username by http session
    @Override
    public String getSessionUserName(HttpSession session) {
        return (String) session.getAttribute(USEREMAIL);
    }
    // Function to get profile of id user
    @Override
    public UserDTO getProfileMe(UUID id) throws NotFoundException{
        try{
            UserDTO response = userRepository.getUserById(id);
            if(response == null){
                throw new NotFoundException(Constants.MESSAGE_GET_NOT_FOUND);
            }
            return response;
        }catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
        }

    }
    // Function to handle login
    @Override
    public LoginResponse loginHandle(String username, String password) {
        Optional<User> model =  userRepository.findUserByUsername(username);
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USERID, model.get().getId());
        session.setAttribute(USEREMAIL, model.get().getUsername());
        if (passwordEncoder.matches(password, model.get().getPassword())) {
            String jwt = jwtUtils.createToken(username, model.get().getUserRole());
            LoginResponse response = new LoginResponse(
                    Constants.MESSAGE_LOGIN_SUCCESS,
                    jwt,
                    model.get().getId(),
                    model.get().getFullname(),
                    model.get().getUsername(),
                    Collections.singletonList(model.get().getUserRole())
            );

            return response;
        }
        else{
            throw new BadRequestException(Constants.MESSAGE_INVALID_PASSWORD);
        }
    }
    // Function to handle register
    @Override
    public ResponseEntity<GenericResponse> registerHandle(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword()))
            throw new BadRequestException(Constants.MESSAGE_INVALID_MATCH_PASSWORD);
        Matcher matcher = pattern.matcher(request.getUsername());
        if(!matcher.matches()){
            GenericResponse response = new GenericResponse(Constants.MESSAGE_INVALID_USERNAME, false);
            return new ResponseEntity<GenericResponse>(response,HttpStatus.BAD_REQUEST);
        }
        try{
            User user = userMapper.RegisterRequestToModel(request);
            userRepository.save(user);
            GenericResponse response = new GenericResponse(Constants.MESSAGE_REGISTER_WELCOME);
            return new ResponseEntity<GenericResponse>(response,HttpStatus.OK);
        }
    catch (RuntimeException e){
            throw new BadRequestException(e.getMessage());
    }
    }
    // Function to get user by id user
    @Override
    public User getUserById(UUID id) {
        return userRepository.findUserById(id);
    }
    // Function to load user by username. The function will be used in order to set session and get user to
    // get UserDetail, create token
    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException  {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USERID, user.getId());
        session.setAttribute(USEREMAIL, user.getUsername());
        return new UserDetailsImplement(user);
    }


}
//}
