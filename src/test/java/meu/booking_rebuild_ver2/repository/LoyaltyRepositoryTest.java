package meu.booking_rebuild_ver2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.service.abstractions.Admin.ILoyaltyService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LoyaltyRepositoryTest {
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Test
    public void LoyalRepository_SaveAll_ReturnSavedLoyalties(){
        Long databaseSize = (Long) entityManager.createQuery("SELECT COUNT(l) FROM Loyalty l").getSingleResult();
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("gold")
                .discount(20)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Loyalty loyalty1 = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("bronze")
                .discount(5)
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
    @Test
    public void LoyalRepository_DeleteById(){
         UUID id = UUID.fromString("9c1f7deb-d63f-493b-804c-eee9fc9ce998");
        Optional<Loyalty> model = loyaltyRepository.findById(id);
        assertThat(model.isPresent());
        loyaltyRepository.deleteById(id);
        assertThat(model.isEmpty());
    }
    @Test
    public void LoyalRepository_GetAll_ReturnListOrderedAscByDiscount(){
        Loyalty loyalty1 = new Loyalty(UUID.randomUUID(),"gold", 20);
        Loyalty loyalty2 = new Loyalty(UUID.randomUUID(),"bronze", 5);
        Loyalty loyalty3 = new Loyalty(UUID.randomUUID(),"platinum", 30);
        Loyalty loyalty4 = new Loyalty(UUID.randomUUID(),"diamond", 10);
        Iterable<Loyalty> loyalties = loyaltyRepository.findAll();
        List<Loyalty> sortedList = StreamSupport.stream(loyalties.spliterator(), false)
                .collect(Collectors.toList());

        for (int i = 0; i < sortedList.size() - 1; i++) {
            Loyalty current = sortedList.get(i);
            Loyalty next = sortedList.get(i + 1);
            assertThat(current.getDiscount()).isLessThanOrEqualTo(next.getDiscount());
        }
    }
//    @Test
//    public void LoyalRepostiory_Update(){
//        UUID id = UUID.fromString("9c1f7deb-d63f-493b-804c-eee9fc9ce998");
//        Optional<Loyalty> model = loyaltyRepository.findById(id);
//    }
}
