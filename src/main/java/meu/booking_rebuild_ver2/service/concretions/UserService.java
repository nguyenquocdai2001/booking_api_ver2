package meu.booking_rebuild_ver2.service.concretions;

import jakarta.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
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
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {
    public static final String USERID = "USER_ID";
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
    public UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtils jwtUtils) {
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }
    @Override
    public UUID getSessionUserId(@NotNull HttpSession session) {
        return (UUID) session.getAttribute(USERID);
    }

    @Override
    public LoginResponse loginHandle(String username, String password) {
        Optional<User> model =  userRepository.findUserByUsername(username);
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

    @Override
    public ResponseEntity<GenericResponse> registerHandle(User user) {
        if (!user.getPassword().equals(user.getConfirmPass()))
            throw new BadRequestException(Constants.MESSAGE_INVALID_MATCH_PASSWORD);
        Matcher matcher = pattern.matcher(user.getUsername());
        if(!matcher.matches()){
            GenericResponse response = new GenericResponse(Constants.MESSAGE_INVALID_USERNAME, false);
            return new ResponseEntity<GenericResponse>(response,HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        GenericResponse response = new GenericResponse(Constants.MESSAGE_REGISTER_WELCOME);
        return new ResponseEntity<GenericResponse>(response,HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException  {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USERID, user.getId());
        return new UserDetailsImplement(user);
    }

}
//}
