package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.TimeRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class TimeRepositoryTests {

    @Autowired
    private TimeRepository timeRepo;

    @Autowired
    private StatusRepository statusRepo;
    @Test
    public void timeRepo_AddAndFindByID_ReturnTime(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);


        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel timeModelSaved = timeRepo.save(time);

        Assertions.assertThat(timeModelSaved).isNotNull();
        Assertions.assertThat(timeRepo.findTimeModelById(timeModelSaved.getId()).getStartDate()).isEqualTo("19-01-2024");
    }

    @Test
    public void timeRepo_Update_ReturnTime(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
                timeRepo.save(time);

        Status status1 = Status.builder().status("Disable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);

        TimeModel timeUpdate = timeRepo.findTimeModelById(time.getId());
            timeUpdate.setStartTime(Time.valueOf("05:30:00"));
            timeUpdate.setEndTime(Time.valueOf("05:30:00"));
            timeUpdate.setStartDate("20-01-2024");
            timeUpdate.setEndDate("23-01-2024");
            timeUpdate.setStatus(statusSaved1);
        TimeModel timeModelUpdateSaved = timeRepo.save(timeUpdate);

        Assertions.assertThat(timeModelUpdateSaved.getStartTime()).isNotNull();
        Assertions.assertThat(timeModelUpdateSaved.getEndTime()).isNotNull();
        Assertions.assertThat(timeModelUpdateSaved.getStartDate()).isNotNull();
        Assertions.assertThat(timeModelUpdateSaved.getEndDate()).isNotNull();
        Assertions.assertThat(timeModelUpdateSaved.getStatus()).isNotNull();
        Assertions.assertThat(timeModelUpdateSaved.getStartTime()).isEqualTo(Time.valueOf("05:30:00"));
        Assertions.assertThat(timeModelUpdateSaved.getEndTime()).isEqualTo(Time.valueOf("05:30:00"));
        Assertions.assertThat(timeModelUpdateSaved.getStartDate()).isEqualTo("20-01-2024");
        Assertions.assertThat(timeModelUpdateSaved.getEndDate()).isEqualTo("23-01-2024");
        Assertions.assertThat(timeModelUpdateSaved.getStatus().getStatus()).isEqualTo("Disable");
    }
    @Test
    public void timeRepo_GetAll_ReturnTime(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel time1 = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("20-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
         timeRepo.save(time);
         timeRepo.save(time1);
        List<TimeModel> list  = timeRepo.findAll();
        Assertions.assertThat(list).contains(time, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("19-01-2024");

        Assertions.assertThat(list).contains(time1, Index.atIndex(1));
        Assertions.assertThat(list.get(1).getStartDate()).isEqualTo("20-01-2024");

    }
    @Test
    public void timeRepo_GetByStatus_ReturnListTime(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        Status status1 = Status.builder().status("Disable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);

        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel time1 = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("20-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel time2 = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("22-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved1).build();

        timeRepo.save(time);
        timeRepo.save(time1);
        timeRepo.save(time2);

        List<TimeModel> list  = timeRepo.getTimeByStatus(statusSaved.getId());

        List<TimeModel> list1  = timeRepo.getTimeByStatus(statusSaved1.getId());

        Assertions.assertThat(list).size().isEqualTo(2);
        Assertions.assertThat(list).contains(time, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("19-01-2024");

        Assertions.assertThat(list).contains(time1, Index.atIndex(1));
        Assertions.assertThat(list.get(1).getStartDate()).isEqualTo("20-01-2024");

        Assertions.assertThat(list1).size().isEqualTo(1);
        Assertions.assertThat(list1).contains(time2, Index.atIndex(0));
        Assertions.assertThat(list1.get(0).getStartDate()).isEqualTo("22-01-2024");

    }

    @Test
    public void timeRepo_DeleteByID_ReturnTime(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel time1 = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("20-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();

        TimeModel timeSave   = timeRepo.save(time);
        TimeModel timeSave1  = timeRepo.save(time1);

        timeRepo.deleteById(timeSave.getId());
        List<TimeModel> list  = timeRepo.findAll();

        Assertions.assertThat(list).size().isEqualTo(1);
        Assertions.assertThat(list).contains(timeSave1, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("20-01-2024");

    }
}
