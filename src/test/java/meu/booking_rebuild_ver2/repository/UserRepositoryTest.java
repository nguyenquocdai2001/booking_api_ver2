package meu.booking_rebuild_ver2.repository;


import meu.booking_rebuild_ver2.model.AuthProvider;
import meu.booking_rebuild_ver2.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void UserRepository_SaveAll_ReturnSavedUser(){
        User user = User
                .builder()
                .fullname("test1")
                .username("testcase1@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .build();
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getFullname()).isEqualTo("test1");
        assertThat(savedUser.getUsername()).isEqualTo("testcase1@gmail.com");
    }
    @Test
    public void UserRepository_Save_ReturnValidUser() {
        User user = User.builder()
                .fullname("John Doe")
                .username("john.doe@example.com")
                .password("password123")
                .confirmPass("password123")
                .createdAt(Instant.now())
                .authProvider(AuthProvider.LOCAL)
                .build();
        userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findUserByUsername("john.doe@example.com");
        assertTrue(retrievedUser.isPresent());
        assertEquals("John Doe", retrievedUser.get().getFullname());
    }
    @Test
    public void testFindUserByUsernameNotFound() {
        Optional<User> retrievedUser = userRepository.findUserByUsername("nonexistent_user@example.com");
        assertFalse(retrievedUser.isPresent());
    }
}
