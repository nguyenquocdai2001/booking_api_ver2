package meu.booking_rebuild_ver2.service.concretions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.impl.UserDetailsImplement;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    public static final String USERID = "USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.modelMapper = new ModelMapper();
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Integer getSessionUserId(@NotNull HttpSession session) {
        return (Integer) session.getAttribute(USERID);
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
                    Collections.singletonList(model.get().getUserRole()));
            return response;
        }
        else{
            throw new BadRequestException(Constants.MESSAGE_INVALID_PASSWORD);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException  {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USER_EMAIL, user.getUsername());
        return new UserDetailsImplement(user);
    }

}
//}
