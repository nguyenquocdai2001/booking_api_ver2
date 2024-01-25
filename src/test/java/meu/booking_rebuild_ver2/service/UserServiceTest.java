package meu.booking_rebuild_ver2.service;

import jakarta.servlet.http.HttpSession;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.AuthProvider;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserRole;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.response.LoginResponse;
import meu.booking_rebuild_ver2.service.abstractions.IUserService;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import meu.booking_rebuild_ver2.service.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private HttpSession httpSession;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getSessionUserId(){
        when(httpSession.getAttribute(UserService.USERID )).thenReturn(1);
        assertEquals(1, userService.getSessionUserId(httpSession));
        System.out.println(httpSession);
    }
    @Test
    void loginHandle_Success(){
        User mockUser = new User();
        mockUser.setUsername("tester2@gmail.com");
        mockUser.setPassword(passwordEncoder.encode("12345678"));

        when(userRepository.findUserByUsername("tester2@gmail.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("12345678", mockUser.getPassword())).thenReturn(true);
        when(jwtUtils.createToken("tester2@gmail.com", mockUser.getUserRole())).thenReturn("mockToken");

        LoginResponse response = userService.loginHandle("tester2@gmail.com", "12345678");
        assertNotNull(response);
        assertEquals(Constants.MESSAGE_LOGIN_SUCCESS, response.getMessage());
        assertEquals("mockToken", response.getToken());
        assertEquals(mockUser.getId(), response.getId());
        assertEquals(mockUser.getFullname(), response.getFullname());
        assertEquals("tester2@gmail.com", response.getUsername());
        assertEquals(Collections.singletonList(mockUser.getUserRole()), response.getRoles());
    }
    @Test
    void loginHandle_InvalidPassword() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword(passwordEncoder.encode("testpassword"));

        when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongpassword", mockUser.getPassword())).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.loginHandle("testuser", "wrongpassword"));

        assertEquals(Constants.MESSAGE_INVALID_PASSWORD, exception.getMessage());
    }
    @Test
    void registerHandle_Success() {
        User mockUser = User.builder()
                .id(UUID.randomUUID())
                .fullname("Test User")
                .username("testuser@gmail.com")
                .password("testpassword")
                .confirmPass("testpassword")
                .createdAt(Instant.now())
                .authProvider(AuthProvider.LOCAL)
                .userRole(UserRole.ROLE_SUPER_ADMIN)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        assertNotNull(mockUser.getFullname());
        assertNotNull(mockUser.getUsername());
        assertNotNull(mockUser.getPassword());
        assertNotNull(mockUser.getConfirmPass());

        ResponseEntity<GenericResponse> responseEntity = userService.registerHandle(mockUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Constants.MESSAGE_REGISTER_WELCOME, responseEntity.getBody().getMessage());
    }

        @Test
        void registerHandle_InvalidUsername() {
            User mockUser = new User();
            mockUser.setUsername("invalidusername");
            mockUser.setPassword("testpassword");
            mockUser.setConfirmPass("testpassword");

            ResponseEntity<GenericResponse> responseEntity = userService.registerHandle(mockUser);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
            assertEquals(Constants.MESSAGE_INVALID_USERNAME, responseEntity.getBody().getMessage());
        }

        @Test
        void loadUserByUsername_UserNotFound() {
            when(userRepository.findUserByUsername("nonexistentuser@gmail.com")).thenReturn(Optional.empty());
            UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                    () -> userService.loadUserByUsername("nonexistentuser@gmail.com"));

            assertEquals("Not found", exception.getMessage());
        }
    }
