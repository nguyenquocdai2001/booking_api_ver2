package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.Driver;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import meu.booking_rebuild_ver2.repository.Admin.DriverRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DriverRepositoryTest {
    @Autowired
    private BusTypesRepository busTypesRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void DriverRepository_Save_ReturnDriverSaved(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        Status status = Status.builder()
                .status("test1")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);

        Driver driver = Driver.builder()
                .name("test")
                .phone("0000000000")
                .kindOfLicense("B2")
                .idBusTypes(busTypesSaved)
                .status(statusSaved)
                .build();
        Driver driverSaved = driverRepository.save(driver);

        Assertions.assertThat(driverSaved).isNotNull();
        Assertions.assertThat(driverSaved.getId()).isNotNull();
        Assertions.assertThat(driverSaved.getName()).isEqualTo("test");
    }

    @Test
    public void DriverRepository_FindAll_ReturnDriverList(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        Status status = Status.builder()
                .status("test1")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);

        Driver driver = Driver.builder()
                .name("test")
                .phone("0000000000")
                .kindOfLicense("B2")
                .idBusTypes(busTypesSaved)
                .status(statusSaved)
                .build();

        Driver driver2 = Driver.builder()
                .name("test2")
                .phone("0000000002")
                .kindOfLicense("B2")
                .idBusTypes(busTypesSaved)
                .status(statusSaved)
                .build();
        driverRepository.save(driver);
        driverRepository.save(driver2);

        List<Driver> driverList = driverRepository.findAll();

        Assertions.assertThat(driverList).isNotNull();
        Assertions.assertThat(driverList).contains(driver, Index.atIndex(1));
        Assertions.assertThat(driverList).contains(driver2, Index.atIndex(2));
        Assertions.assertThat(driverList.size()).isGreaterThan(0);
    }

    @Test
    public void DriverRepository_FindById_ReturnDriverById(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        Status status = Status.builder()
                .status("test1")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);

        Driver driver = Driver.builder()
                .name("test")
                .phone("0000000000")
                .kindOfLicense("B2")
                .idBusTypes(busTypesSaved)
                .status(statusSaved)
                .build();
        driverRepository.save(driver);

        Driver driverById = driverRepository.findById(driver.getId()).get();

        Assertions.assertThat(driverById).isNotNull();
        Assertions.assertThat(driverById.getStatus().getId()).isEqualTo(statusSaved.getId());
    }
}
