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
import java.util.Optional;

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
        Assertions.assertThat(statusSaved).isNotNull();
        Assertions.assertThat(statusSaved.getStatus()).isEqualTo("Enable");
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

    @Test
    public void statusRepository_findById_ReturnStatus(){
        //Arrange
        Status status = Status.builder().status("Enable").flag(true).build();

        Status statusSaved = statusRepository.save(status);

        Status statusTest = statusRepository.findStatusById(statusSaved.getId());

        //Assert
        Assertions.assertThat(statusTest).isNotNull();

    }

    @Test
    public void statusRepository_findAllByFlag_ReturnListStatus(){
        //Arrange
        Status status = Status.builder().status("Enable").flag(true).build();
        Status status2 = Status.builder().status("Diable").flag(false).build();
        Status status3 = Status.builder().status("Enable3").flag(true).build();

        statusRepository.save(status);
        statusRepository.save(status2);
        statusRepository.save(status3);

        List<Status> statusTest = statusRepository.findAllByFlag(true);
        List<Status> statusTest1 = statusRepository.findAllByFlag(false);

        //Assert
        Assertions.assertThat(statusTest).isNotNull();
        Assertions.assertThat(statusTest1).isNotNull();
    }

    @Test
    public void StatusRepository_UpdateStatus_ReturnStatusNotNull(){
        Status status = Status.builder().status("Enable").flag(true).build();

        statusRepository.save(status);

        Status statusSaved = statusRepository.findById(status.getId()).get();
        statusSaved.setStatus("Enable updated");
        statusSaved.setFlag(false);

        Status statusUpdated = statusRepository.save(statusSaved);

        //Assert
        Assertions.assertThat(statusUpdated.getStatus()).isNotNull();
        Assertions.assertThat(statusUpdated.isFlag()).isFalse();
    }

    @Test
    public void StatusRepository_DeleteStatus_ReturnStatusIsEmpty(){
        Status status = Status.builder().status("Enable").flag(true).build();

        statusRepository.save(status);

        statusRepository.deleteById(status.getId());
        Optional<Status> statusReturn = statusRepository.findById(status.getId());

        //Assert
        Assertions.assertThat(statusReturn).isEmpty();

    }
}
