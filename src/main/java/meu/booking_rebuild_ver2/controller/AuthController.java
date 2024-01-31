package meu.booking_rebuild_ver2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.DTO.UserDTO;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserID;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.request.LoginRequest;
import meu.booking_rebuild_ver2.request.RegisterRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.springframework.http.ResponseEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    private UserID userID ;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<GenericResponse> addNewUser(@RequestBody @Valid RegisterRequest user) {
        try {
            return userService.registerHandle(user);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public LoginResponse loginHandle(@RequestBody LoginRequest loginRequest, HttpSession session){
        try{
            LoginResponse response;

            String password = loginRequest.getPassword();
            String email = loginRequest.getEmail();
            response = userService.loginHandle(email, password);
            if(session.getAttribute("USER_ID") != null) {
                userID.setUserValue((UUID)session.getAttribute("USER_ID"));
                return response;
            }
            return new LoginResponse("Login Fail", false);
        }

        catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    @GetMapping("profile/me")
    public UserDTO getProfileMe(@RequestParam UUID id) throws NotFoundException {
        return userService.getProfileMe(id);
    }
//    @PostMapping(path = "/login")
//    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) {
//        try {
//            String password = loginRequest.getPassword();
//            String email = loginRequest.getEmail();
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(email, password));
//            System.out.println(authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtils.generateJwtToken(authentication);
//            UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(item -> item.getAuthority())
//                    .collect(Collectors.toList());
//            httpSession.setAttribute(UserService.USERID, userDetails.getId());
//            LoginResponse response = new LoginResponse(
//                    Constants.MESSAGE_LOGIN_SUCCESS,
//                    jwt,
//                    userDetails.getId(),
//                    userDetails.getUsername(),
//                    userDetails.getEmail(),
//                    roles);
//            return response;
//        } catch (Exception ex) {
//            throw new BadRequestException(ex.getMessage());
//        }
//
//    }


}
