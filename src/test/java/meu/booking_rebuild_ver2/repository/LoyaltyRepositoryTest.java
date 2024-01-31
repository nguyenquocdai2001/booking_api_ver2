package meu.booking_rebuild_ver2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/*
author: Nguyen Minh Tam
test for bs-2
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LoyaltyRepositoryTest {
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    @PersistenceContext
    private EntityManager entityManager;
    // Function test save all and get all
    @Test
    public void LoyalRepository_SaveAll_ReturnSavedLoyalties(){
        Long databaseSize = (Long) entityManager.createQuery("SELECT COUNT(l) FROM Loyalty l").getSingleResult();
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("gold")
                .discount(20)
                .loyaltySpent(5.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Loyalty loyalty1 = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("bronze")
                .discount(5)
                .loyaltySpent(3.500)
                .build();
        Loyalty loyalty1Saved = loyaltyRepository.save(loyalty1);
        Loyalty loyalty2 = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("plantium")
                .discount(30)
                .build();
        Loyalty loyalty2Saved = loyaltyRepository.save(loyalty2);
        Loyalty loyalty3 = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        assertThat(loyaltySaved).isNotNull();
        assertThat(loyalty1Saved).isNotNull();
        assertThat(loyalty2Saved).isNotNull();
        assertThat(loyalty3Saved).isNotNull();
        assertThat(loyaltySaved).isNotEqualTo(loyalty1Saved);
        assertThat(loyaltySaved).isNotEqualTo(loyalty2Saved);
        assertThat(loyaltySaved).isNotEqualTo(loyalty3Saved);

        assertThat(loyalty1Saved).isNotEqualTo(loyaltySaved);
        assertThat(loyalty1Saved).isNotEqualTo(loyalty2Saved);
        assertThat(loyalty1Saved).isNotEqualTo(loyalty3Saved);

        assertThat(loyalty2Saved).isNotEqualTo(loyaltySaved);
        assertThat(loyalty2Saved).isNotEqualTo(loyalty1Saved);
        assertThat(loyalty2Saved).isNotEqualTo(loyalty3Saved);

        assertThat(loyalty3Saved).isNotEqualTo(loyaltySaved);
        assertThat(loyalty3Saved).isNotEqualTo(loyalty1Saved);
        assertThat(loyalty3Saved).isNotEqualTo(loyalty2Saved);
        Long updatedDatabaseSize = (Long) entityManager.createQuery("SELECT COUNT(l) FROM Loyalty l").getSingleResult();
        assertThat(updatedDatabaseSize).isEqualTo(databaseSize + 4);
    }
    // FUnction test delete
    @Test
    public void LoyalRepository_DeleteById(){
        Loyalty loyalty3 = Loyalty.builder()
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        Optional<Loyalty> model = loyaltyRepository.findById(loyalty3Saved.getId());
        assertThat(model.isPresent());
        loyaltyRepository.deleteById(loyalty3Saved.getId());
         model = loyaltyRepository.findById(loyalty3Saved.getId());
        assertThat(model.isEmpty());
    }
    // FUnction test update
    @Test
    public void LoyalRepository_Update_ReturnLoyalty(){
        Status status = Status.builder().status("test").flag(true).build();
        Status modelStatus = statusRepository.save(status);
        User user = User.builder().username("abc@gmail.com").password("string").confirmPass("string").fullname("abc").status(modelStatus).build();
        user = userRepository.save(user);
        Loyalty loyalty3 = Loyalty.builder()
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .UserConfig(user)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        Optional<Loyalty> model = loyaltyRepository.findById(loyalty3Saved.getId());
        assertThat(model.isPresent());
        model.get().setRank("gold");
        model.get().setDiscount(30);
        model.get().setLoyaltySpent(10.2);
       Loyalty loyalty =  loyaltyRepository.save(model.get());
        assertThat(loyalty != null);
        model = loyaltyRepository.findById(loyalty.getId());
        assertThat(model.get().getRank() == "diamond");
        assertThat(model.get().getDiscount() == 30);
        assertThat(model.get().getLoyaltySpent() == 10.2);
    }
    // Function test get all and orders by discount
    @Test
    public void LoyalRepository_GetAll_ReturnListOrderedAscByDiscount(){
        Loyalty loyalty1 = new Loyalty(UUID.randomUUID(),"gold", 20, 3.500);
        Loyalty loyalty2 = new Loyalty(UUID.randomUUID(),"bronze", 5, 4.500);
        Loyalty loyalty3 = new Loyalty(UUID.randomUUID(),"platinum", 30, 5.500);
        Loyalty loyalty4 = new Loyalty(UUID.randomUUID(),"diamond", 10, 6.500);
        ArrayList<Loyalty> loyalties = new ArrayList<>();
        loyalties.add(loyalty1);
        loyalties.add(loyalty2);
        loyalties.add(loyalty3);
        loyalties.add(loyalty4);
        loyaltyRepository.saveAll(loyalties);
        Iterable<LoyaltyDTO> loyaltiesList = loyaltyRepository.findAll(Sort.by(Sort.Direction.ASC, "discount"));
        List<LoyaltyDTO> sortedList = StreamSupport.stream(loyaltiesList.spliterator(), false)
                .toList();

        for (int i = 0; i < sortedList.size() - 1; i++) {
            LoyaltyDTO current = sortedList.get(i);
            LoyaltyDTO next = sortedList.get(i + 1);
            assertThat(current.getDiscount()).isLessThanOrEqualTo(next.getDiscount());
        }
    }
    // FUnction test get by rank
    @Test
    public void LoyalRepository_GetByRank_ReturnLoyalty(){
        Status status = Status.builder().status("test").flag(true).build();
        Status modelStatus = statusRepository.save(status);
        User user = User.builder().username("abc@gmail.com").password("string").confirmPass("string").fullname("abc").status(modelStatus).build();
        user = userRepository.save(user);
        Loyalty loyalty3 = Loyalty.builder()
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .UserConfig(user)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        assertThat(loyalty3Saved != null);
        Optional<Loyalty> model = loyaltyRepository.findByRank(loyalty3Saved.getRank());
        assertThat(model.isPresent());
        assertThat(model.get().getRank() == "diamond");
    }
    // Function test get by price
    @Test
    public void LoyalRepository_GetByPrice_ReturnLoyalty(){
        Status status = Status.builder().status("test").flag(true).build();
        Status modelStatus = statusRepository.save(status);
        User user = User.builder().username("abc@gmail.com").password("string").confirmPass("string").fullname("abc").status(modelStatus).build();
        user = userRepository.save(user);
        Loyalty loyalty3 = Loyalty.builder()
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .UserConfig(user)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        assertThat(loyalty3Saved != null);
        Optional<Loyalty> model = loyaltyRepository.getLoyaltyByPrice(7);
        assertThat(model.get().getRank() == "diamond");
        assertThat(model.get().getDiscount() == 50);
        assertThat(model.get().getLoyaltySpent() == 6.5);
    }
    // FUnction get by discount
    @Test
    public void LoyaltyRepository_GetByDiscount_ReturnLoyalty(){
        Status status = Status.builder().status("test").flag(true).build();
        Status modelStatus = statusRepository.save(status);
        User user = User.builder().username("abc@gmail.com").password("string").confirmPass("string").fullname("abc").status(modelStatus).build();
        user = userRepository.save(user);
        Loyalty loyalty3 = Loyalty.builder()
                .rank("diamond")
                .discount(50)
                .loyaltySpent(6.500)
                .UserConfig(user)
                .build();
        Loyalty loyalty3Saved = loyaltyRepository.save(loyalty3);
        Optional<Loyalty> loyalty = loyaltyRepository.findByDiscount(50);
        Assertions.assertNotNull(loyalty);
        Assertions.assertEquals(loyalty3Saved.getId(), loyalty.get().getId());
    }
}
