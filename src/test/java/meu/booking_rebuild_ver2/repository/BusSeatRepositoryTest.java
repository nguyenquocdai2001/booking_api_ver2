package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.repository.Admin.BusSeatRepository;
import meu.booking_rebuild_ver2.repository.Admin.BusTypesRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BusSeatRepositoryTest {
    @Autowired
    private BusSeatRepository busSeatRepository;

    @Autowired
    private BusTypesRepository busTypesRepository;

    @Test
    public void BusSeatRepository_Save_ReturnSavedBusSeat(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();
        BusSeat busSeatSaved = busSeatRepository.save(busSeat);

        Assertions.assertThat(busSeatSaved).isNotNull();
        Assertions.assertThat(busSeatSaved.getId()).isNotNull();
        Assertions.assertThat(busSeatSaved.getNameSeat()).isEqualTo("A1");
    }

    @Test
    public void BusSeatRepository_FindAll_ReturnSavedBusSeatList(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();
        BusSeat busSeat2 = BusSeat.builder().
                isAvailable(true).
                nameSeat("A2").
                floorNumber(1).build();
        busSeatRepository.save(busSeat);
        busSeatRepository.save(busSeat2);

        List<BusSeat> busSeatList = busSeatRepository.findAll();

        Assertions.assertThat(busSeatList).isNotNull();
        Assertions.assertThat(busSeatList).contains(busSeat, Index.atIndex(1));
        Assertions.assertThat(busSeatList).contains(busSeat2, Index.atIndex(2));
        Assertions.assertThat(busSeatList.size()).isGreaterThan(0);
    }

    @Test
    public void BusSeatRepository_FindById_ReturnBusSeatById(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();
        busSeatRepository.save(busSeat);

        BusSeat busSeatById = busSeatRepository.findById(busSeat.getId()).get();


        Assertions.assertThat(busSeatById).isNotNull();
    }

    @Test
    public void BusSeatRepository_FindAllByIdBusType_ReturnAllBusSeatByIdBusType(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();

        BusSeat busSeat2 = BusSeat.builder().
                isAvailable(true).
                nameSeat("A2").
                floorNumber(1).build();
        busSeatRepository.save(busSeat);
        busSeatRepository.save(busSeat2);

        List<BusSeat> busSeatByIdBusType = busSeatRepository.findAllByIdBusTypes(busTypesSaved.getId());


        Assertions.assertThat(busSeatByIdBusType).isNotNull();
        Assertions.assertThat(busSeatByIdBusType.get(0).getIdBusTypes().getId()).isEqualTo(busTypesSaved.getId());
        Assertions.assertThat(busSeatByIdBusType.size()).isGreaterThan(0);
    }

    @Test
    public void BusSeatRepository_FindByAllIsAvailable_ReturnAllBusSeatByIsAvailable(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();

        BusSeat busSeat2 = BusSeat.builder().
                isAvailable(false).
                nameSeat("A2").
                floorNumber(1).build();
        busSeatRepository.save(busSeat);
        busSeatRepository.save(busSeat2);

        List<BusSeat> busSeatByIdBusType = busSeatRepository.findAllByIsAvailable(true);

        Assertions.assertThat(busSeatByIdBusType).isNotNull();
        Assertions.assertThat(busSeatByIdBusType.get(0).isAvailable()).isEqualTo(true);
        Assertions.assertThat(busSeatByIdBusType.size()).isGreaterThan(0);
    }

    @Test
    public void BusSeatRepository_UpdateBusSeat_ReturnBusSeatUpdated(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();
        busSeatRepository.save(busSeat);

        BusSeat busSeatById = busSeatRepository.findById(busSeat.getId()).get();
        busSeatById.setNameSeat("Test");
        busSeatById.setFloorNumber(1);

        BusSeat updatedBusSeat = busSeatRepository.save(busSeatById);


        Assertions.assertThat(updatedBusSeat.getNameSeat()).isNotNull();
        Assertions.assertThat(updatedBusSeat.getNameSeat()).isEqualTo("Test");
        Assertions.assertThat(updatedBusSeat.getFloorNumber()).isEqualTo(1);
    }

    @Test
    public void BusSeatRepository_DeleteBusSeat_ReturnBusSeatIsEmpty(){
        BusTypes busTypes = BusTypes.builder().
                type("String1").
                numberOfSeat(32).
                licensePlate("ABCED").build();
        BusTypes busTypesSaved = busTypesRepository.save(busTypes);

        BusSeat busSeat = BusSeat.builder().
                isAvailable(true).
                nameSeat("A1").
                floorNumber(2).
                idBusTypes(busTypesSaved).build();
        busSeatRepository.save(busSeat);
        busSeatRepository.deleteById(busSeat.getId());

        Optional<BusSeat> busSeatReturn = busSeatRepository.findById(busSeat.getId());

        Assertions.assertThat(busSeatReturn).isEmpty();
    }
}
