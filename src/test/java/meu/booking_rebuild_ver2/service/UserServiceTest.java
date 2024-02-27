package meu.booking_rebuild_ver2.service;

import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.model.Admin.Mapper.UserMapper;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.model.UserRole;
import meu.booking_rebuild_ver2.repository.StatusRepository;
import meu.booking_rebuild_ver2.repository.UserRepository;
import meu.booking_rebuild_ver2.request.RegisterRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;
import meu.booking_rebuild_ver2.service.concretions.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class UserServiceTest {
    @Autowired
    private StatusRepository statusRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;
    @Test
    void testRegisterHandle_Success_ReturnGenericResponse() {
        Status status = new Status(UUID.randomUUID(),"testStatus",true);
        RegisterRequest request = new RegisterRequest("John Doe", "johndoe@example.com", "password", "password", status.getId());
        User user = new User("John Doe", "johndoe@example.com", "password", "password",status );
        System.out.println(userRepository.count());
        User user1 = User.builder()
                .status(status)
                .username("minhteo@gmail.com")
                .confirmPass("password")
                .password("password")
                .fullname("Minh Teo").build();
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User model = userRepository.save(user1);
        ResponseEntity<GenericResponse> responseEntity = userService.registerHandle(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Registered! Welcome.", responseEntity.getBody().getMessage());
        System.out.println(model);
    }

    @Test
    public void testRegisterHandle_InvalidPassword_ReturnGenericResponse() {
        RegisterRequest request = new RegisterRequest("John Doe", "johndoe@example.com", "password", "different_password", UUID.randomUUID());
        BadRequestException exception = org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class, () -> {
            userService.registerHandle(request);
        });
        assertEquals("Request is malformed: Passwords don't match.", exception.getMessage()); // Assuming the expected error message is "Invalid password"
    }
    @Test
    public void testLoginHandle_Success_ReturnLoginResponse(){

    }
}
