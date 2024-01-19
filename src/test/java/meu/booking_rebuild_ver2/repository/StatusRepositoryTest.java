package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class StatusRepositoryTest {
    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void statusRepository_save_ReturnObjectStatus(){
        //Arrange
        Status status = Status.builder().status("Enable").flag(true).build();

        Status statusSaved = statusRepository.save(status);

        //Assert
        Assertions.assertThat(statusSaved.getId()).isNotNull();
        Assertions.assertThat(statusSaved).isNotNull();

    }

    @Test
    public void statusRepository_findAll_ReturnListStatus(){
        //Arrange
        Status status = Status.builder().status("Enable").flag(true).build();
        Status status2 = Status.builder().status("Diable").flag(true).build();

        statusRepository.save(status);
        statusRepository.save(status2);

        List<Status> statusListTest = statusRepository.findAll();


        //Assert
        Assertions.assertThat(statusListTest).isNotNull();

    }
}
