package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.*;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class PriceRepositoryTests {

    @Autowired
    private PriceRepository priceRepo;

    @Autowired
    private StatusRepository statusRepo;
    @Autowired
    private TimeRepository timeRepo;
    @Autowired
    private RoutesRepository routesRepo;
    @Autowired
    private RoutesTimeRepository routesTimeRepo;
    @Autowired
    private BusTypesRepository busTypesRepo;

    @Test
    public void priceRepo_AddAndFindByID_ReturnPrice(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);
        
        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel priceModelSave = priceRepo.save(price);

        Assertions.assertThat(priceModelSave).isNotNull();
        Assertions.assertThat(priceRepo.findPriceModelById(priceModelSave.getId()).getPrice()).isEqualTo("150.000");
        Assertions.assertThat(priceRepo.findPriceModelById(priceModelSave.getId()).getIdBusType().getType()).isEqualTo("test");
    }

    @Test
    public void priceRepo_UpdatePrice_ReturnUpdatedPrice() {
        // Assuming priceModelSave is the PriceModel saved in the previous test
        PriceModel priceModelSave = createPrice(); // Create and save a PriceModel for testing

        PriceModel priceModelToUpdate = priceRepo.findPriceModelById(priceModelSave.getId());
        priceModelToUpdate.setPrice("200.000"); // Updating the price

        PriceModel updatedPriceModel = priceRepo.save(priceModelToUpdate);

        Assertions.assertThat(updatedPriceModel).isNotNull();
        Assertions.assertThat(updatedPriceModel.getPrice()).isEqualTo("200.000");
    }
    @Test
    public void priceRepo_DeletePrice_SuccessfulDeletion() {
        // Assuming priceModelSave is the PriceModel saved in the previous test
        List<PriceModel> list = new ArrayList<>();
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel price1 = PriceModel.builder()
                .price("200.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();
        PriceModel priceModelSave = priceRepo.save(price);
        PriceModel priceModelSave1 = priceRepo.save(price1);
        list.add(priceModelSave);
        list.add(priceModelSave1);
        // Deleting the PriceModel
        priceRepo.deleteById(priceModelSave1.getId());


        Assertions.assertThat(list).size().isEqualTo(1);
        Assertions.assertThat(list).contains(priceModelSave, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getPrice()).isEqualTo("150.000");
    }

    @Test
    public void priceRepo_GetByStatus_ReturnListPrice(){
        List<PriceModel> list = new ArrayList<>();
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel price1 = PriceModel.builder()
                .price("200.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();
        PriceModel priceModelSave = priceRepo.save(price);
        PriceModel priceModelSave1 = priceRepo.save(price1);
        list.add(priceModelSave);
        list.add(priceModelSave1);

        List<PriceModel> listStatus1  = priceRepo.getPriceByStatus(statusSaved.getId());


        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getPrice()).isEqualTo("150.000");

        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(1));
        Assertions.assertThat(listStatus1.get(1).getPrice()).isEqualTo("200.00");

    }
    @Test
    public void priceRepo_GetByBusType_ReturnListPrice(){
        List<PriceModel> list = new ArrayList<>();
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel price1 = PriceModel.builder()
                .price("200.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();
        PriceModel priceModelSave = priceRepo.save(price);
        PriceModel priceModelSave1 = priceRepo.save(price1);
        list.add(priceModelSave);
        list.add(priceModelSave1);

        List<PriceModel> listStatus1  = priceRepo.getPriceByBusType(busTypesSave.getId());


        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getPrice()).isEqualTo("150.000");

        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(1));
        Assertions.assertThat(listStatus1.get(1).getPrice()).isEqualTo("200.00");

    }

    @Test
    public void priceRepo_GetByRouteTime_ReturnListPrice(){
        List<PriceModel> list = new ArrayList<>();
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel price1 = PriceModel.builder()
                .price("200.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();
        PriceModel priceModelSave = priceRepo.save(price);
        PriceModel priceModelSave1 = priceRepo.save(price1);
        list.add(priceModelSave);
        list.add(priceModelSave1);

        List<PriceModel> listStatus1  = priceRepo.getPriceByRoutesTime(routesModelSaved.getId());


        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getPrice()).isEqualTo("150.000");

        Assertions.assertThat(listStatus1).contains(price, Index.atIndex(1));
        Assertions.assertThat(listStatus1.get(1).getPrice()).isEqualTo("200.00");

    }
    private PriceModel createPrice(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("19-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test 1")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("150.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel priceModelSave = priceRepo.save(price);
        return priceModelSave;
    }
}
