package meu.booking_rebuild_ver2.service.concretions;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.impl.UserDetailsImplement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.constraints.NotNull;

@Service
public class UserService implements IUserService {
    public static final String USERID = "USER_ID";
    public static final String USER_EMAIL = "USER_EMAIL";
    private final ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        this.modelMapper = new ModelMapper();
    }
    @Override
    public Integer getSessionUserId(@NotNull HttpSession session) {
        return (Integer) session.getAttribute(USERID);
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();
        session.setAttribute(USER_EMAIL, user.getEmail());
        return new UserDetailsImplement(user);
    }
}
//}
