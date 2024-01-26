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
import java.util.List;

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

//    @Test
//    public void priceRepo_Update_ReturnPrice(){
//        Status status = Status.builder().status("Enable").flag(true).build();
//        Status statusSaved = statusRepo.save(status);
//
//        PriceModel price = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("19-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//                priceRepo.save(price);
//
//        Status status1 = Status.builder().status("Disable").flag(true).build();
//        Status statusSaved1 = statusRepo.save(status1);
//
//        PriceModel priceUpdate = priceRepo.findPriceModelById(price.getId());
//            priceUpdate.setStartPrice(Price.valueOf("05:30:00"));
//            priceUpdate.setEndPrice(Price.valueOf("05:30:00"));
//            priceUpdate.setStartDate("20-01-2024");
//            priceUpdate.setEndDate("23-01-2024");
//            priceUpdate.setStatus(statusSaved1);
//        PriceModel priceModelUpdateSaved = priceRepo.save(priceUpdate);
//
//        Assertions.assertThat(priceModelUpdateSaved.getStartPrice()).isNotNull();
//        Assertions.assertThat(priceModelUpdateSaved.getEndPrice()).isNotNull();
//        Assertions.assertThat(priceModelUpdateSaved.getStartDate()).isNotNull();
//        Assertions.assertThat(priceModelUpdateSaved.getEndDate()).isNotNull();
//        Assertions.assertThat(priceModelUpdateSaved.getStatus()).isNotNull();
//        Assertions.assertThat(priceModelUpdateSaved.getStartPrice()).isEqualTo(Price.valueOf("05:30:00"));
//        Assertions.assertThat(priceModelUpdateSaved.getEndPrice()).isEqualTo(Price.valueOf("05:30:00"));
//        Assertions.assertThat(priceModelUpdateSaved.getStartDate()).isEqualTo("20-01-2024");
//        Assertions.assertThat(priceModelUpdateSaved.getEndDate()).isEqualTo("23-01-2024");
//        Assertions.assertThat(priceModelUpdateSaved.getStatus().getStatus()).isEqualTo("Disable");
//    }
//    @Test
//    public void priceRepo_GetAll_ReturnPrice(){
//        Status status = Status.builder().status("Enable").flag(true).build();
//        Status statusSaved = statusRepo.save(status);
//
//        PriceModel price = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("19-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//
//        PriceModel price1 = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("20-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//         priceRepo.save(price);
//         priceRepo.save(price1);
//        List<PriceModel> list  = priceRepo.findAll();
//        Assertions.assertThat(list).contains(price, Index.atIndex(0));
//        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("19-01-2024");
//
//        Assertions.assertThat(list).contains(price1, Index.atIndex(1));
//        Assertions.assertThat(list.get(1).getStartDate()).isEqualTo("20-01-2024");
//
//    }
//    @Test
//    public void priceRepo_GetByStatus_ReturnListPrice(){
//        Status status = Status.builder().status("Enable").flag(true).build();
//        Status statusSaved = statusRepo.save(status);
//
//        Status status1 = Status.builder().status("Disable").flag(true).build();
//        Status statusSaved1 = statusRepo.save(status1);
//
//        PriceModel price = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("19-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//
//        PriceModel price1 = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("20-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//
//        PriceModel price2 = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("22-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved1).build();
//
//        priceRepo.save(price);
//        priceRepo.save(price1);
//        priceRepo.save(price2);
//
//        List<PriceModel> list  = priceRepo.getPriceByStatus(statusSaved.getId());
//
//        List<PriceModel> list1  = priceRepo.getPriceByStatus(statusSaved1.getId());
//
//        Assertions.assertThat(list).size().isEqualTo(2);
//        Assertions.assertThat(list).contains(price, Index.atIndex(0));
//        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("19-01-2024");
//
//        Assertions.assertThat(list).contains(price1, Index.atIndex(1));
//        Assertions.assertThat(list.get(1).getStartDate()).isEqualTo("20-01-2024");
//
//        Assertions.assertThat(list1).size().isEqualTo(1);
//        Assertions.assertThat(list1).contains(price2, Index.atIndex(0));
//        Assertions.assertThat(list1.get(0).getStartDate()).isEqualTo("22-01-2024");
//
//    }
//
//    @Test
//    public void priceRepo_DeleteByID_ReturnPrice(){
//        Status status = Status.builder().status("Enable").flag(true).build();
//        Status statusSaved = statusRepo.save(status);
//
//        PriceModel price = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("19-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//
//        PriceModel price1 = PriceModel.builder()
//                .startPrice(Price.valueOf("04:30:00"))
//                .endPrice(Price.valueOf("04:30:00"))
//                .startDate("20-01-2024")
//                .endDate("23-01-2024")
//                .status(statusSaved).build();
//
//        PriceModel priceSave   = priceRepo.save(price);
//        PriceModel priceSave1  = priceRepo.save(price1);
//
//        priceRepo.deleteById(priceSave.getId());
//        List<PriceModel> list  = priceRepo.findAll();
//
//        Assertions.assertThat(list).size().isEqualTo(1);
//        Assertions.assertThat(list).contains(priceSave1, Index.atIndex(0));
//        Assertions.assertThat(list.get(0).getStartDate()).isEqualTo("20-01-2024");
//
//    }
}
