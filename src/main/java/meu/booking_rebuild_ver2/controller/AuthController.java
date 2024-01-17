package meu.booking_rebuild_ver2.controller;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.request.LoginRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import meu.booking_rebuild_ver2.service.impl.UserDetailsImplement;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private JwtUtils jwtUtils;
    @Autowired
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(path = "/register")
    public GenericResponse addNewUser(@RequestBody @Valid User user) {
        try {
            if (!user.getPassword().equals(user.getConfirmPass()))
                throw new BadRequestException(Constants.MESSAGE_INVALID_MATCH_PASSWORD);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            GenericResponse response = new GenericResponse(Constants.MESSAGE_REGISTER_WELCOME);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public LoginResponse loginHandle(@RequestBody LoginRequest loginRequest){
        try{
            String password = loginRequest.getPassword();
            String email = loginRequest.getEmail();
            return userService.loginHandle(email, password);

        }

        catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
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
