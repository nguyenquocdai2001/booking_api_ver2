package meu.booking_rebuild_ver2.repository;


import meu.booking_rebuild_ver2.model.AuthProvider;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
/*
author: Nguyen Minh Tam
test: UserRepository test
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    // The test function to test save
    @Test
    public void UserRepository_SaveAll_ReturnSavedUser(){
        Long countPrev = userRepository.count();
        User user = User
                .builder()
                .fullname("test1")
                .username("testcase1@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .build();
        User user1 = User
                .builder()
                .fullname("test2")
                .username("testcase2@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .build();
        User user2 = User
                .builder()
                .fullname("test3")
                .username("testcase3@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .build();
        ArrayList<User> listUser = new ArrayList<>();
        listUser.add(user);
        listUser.add(user1);
        listUser.add(user2);
        Iterable<User> savedUser = userRepository.saveAll(listUser);
        Assertions.assertNotNull(savedUser);
        assertThat(savedUser).isNotNull();
        ArrayList<User> userArrayList = (ArrayList<User>) userRepository.findAll();
        Assertions.assertEquals(countPrev+3, userArrayList.size());
    }
    // Function test to save user
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
    // Function test to get by username
    @Test
    public void UserRepository_Get_ReturnFail() {
        Optional<User> retrievedUser = userRepository.findUserByUsername("nonexistent_user@example.com");
        assertFalse(retrievedUser.isPresent());
    }
    // Function test to update
    @Test
    public void UserRepository_Update_ReturnUser() {
        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);
        User user = User
                .builder()
                .fullname("test1")
                .username("testcase1@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .status(statusSaved)
                .build();
        User model = userRepository.save(user);
        Assertions.assertNotNull(model);
        Assertions.assertEquals("testcase1@gmail.com", model.getUsername());
        model.setFullname("test");
        model.setUsername("testcase@gmail.com");
        model.setCreatedAt(Instant.now());
        userRepository.save(model);
        User modelAfterUpdate = userRepository.findUserById(model.getId());
        Assertions.assertNotNull(modelAfterUpdate);
        Assertions.assertEquals("test", modelAfterUpdate.getFullname());
        Assertions.assertEquals("testcase@gmail.com", modelAfterUpdate.getUsername());
    }
    // Function test delete
    @Test
    public void UserReository_Delete_ReturnUser(){
        Long countPrev = userRepository.count();
        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);
        User user = User
                .builder()
                .fullname("test1")
                .username("testcase1@gmail.com")
                .password("12345678")
                .confirmPass("12345678")
                .status(statusSaved)
                .build();
        User model = userRepository.save(user);
        Assertions.assertEquals(countPrev+1, userRepository.count());
        userRepository.delete(model);
        Optional<User> deletedUser = userRepository.findById(model.getId());
        Assertions.assertEquals(true, deletedUser.isEmpty());
        Assertions.assertEquals(countPrev, userRepository.count());
    }
    // Function test to get all
    @Test
    public void UserRepository_GetAll_ReturnListUser(){
        Long countPrev = userRepository.count();
        ArrayList<User> listUser = (ArrayList<User>) userRepository.findAll();
        Assertions.assertEquals(countPrev, listUser.size());
    }
}
