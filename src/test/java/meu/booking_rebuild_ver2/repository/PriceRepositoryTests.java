package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.RoutesModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.RoutesRepository;
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
public class PriceRepositoryTests {

    @Autowired
    private RoutesRepository routesRepo;

    @Autowired
    private StatusRepository statusRepo;
    @Test
    public void routesRepo_AddAndFindByID_ReturnRoutes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);


        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();

        RoutesModel routesModelSaved = routesRepo.save(routes);

        Assertions.assertThat(routesModelSaved).isNotNull();
        Assertions.assertThat(routesRepo.findRoutesModelById(routesModelSaved.getId()).getDeparturePoint()).isEqualTo("test3");
        Assertions.assertThat(routesRepo.findRoutesModelById(routesModelSaved.getId()).getDestinationPoint()).isEqualTo("test3");
    }

    @Test
    public void routesRepo_Update_ReturnRoutes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();
                routesRepo.save(routes);

        Status status1 = Status.builder().status("Disable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);

        RoutesModel routesUpdate = routesRepo.findRoutesModelById(routes.getId());
            routesUpdate.setDestinationPoint("test4");
            routesUpdate.setDeparturePoint("test5");
            routesUpdate.setStatus(statusSaved1);
        RoutesModel routesModelUpdateSaved = routesRepo.save(routesUpdate);

        Assertions.assertThat(routesModelUpdateSaved.getDestinationPoint()).isNotNull();
        Assertions.assertThat(routesModelUpdateSaved.getDeparturePoint()).isNotNull();
        Assertions.assertThat(routesModelUpdateSaved.getStatus()).isNotNull();
        Assertions.assertThat(routesModelUpdateSaved.getDestinationPoint()).isEqualTo("test4");
        Assertions.assertThat(routesModelUpdateSaved.getDeparturePoint()).isEqualTo("test5");

        Assertions.assertThat(routesModelUpdateSaved.getStatus().getStatus()).isEqualTo("Disable");
    }
    @Test
    public void routesRepo_GetAll_ReturnRoutes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();

        RoutesModel routes1 = RoutesModel.builder()
                .departurePoint("test4")
                .destinationPoint("test4")
                .status(statusSaved).build();
         routesRepo.save(routes);
         routesRepo.save(routes1);
        List<RoutesModel> list  = routesRepo.findAll();
        Assertions.assertThat(list).contains(routes, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getDeparturePoint()).isEqualTo("test3");
        Assertions.assertThat(list.get(0).getDestinationPoint()).isEqualTo("test3");

        Assertions.assertThat(list).contains(routes1, Index.atIndex(1));
        Assertions.assertThat(list.get(1).getDeparturePoint()).isEqualTo("test4");
        Assertions.assertThat(list.get(1).getDestinationPoint()).isEqualTo("test4");
    }
    @Test
    public void routesRepo_GetByStatus_ReturnListRoutes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        Status status1 = Status.builder().status("Disable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);

        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();

        RoutesModel routes1 = RoutesModel.builder()
                .departurePoint("test4")
                .destinationPoint("test4")
                .status(statusSaved).build();

        RoutesModel routes2 = RoutesModel.builder()
                .departurePoint("test5")
                .destinationPoint("test5")
                .status(statusSaved1).build();

        routesRepo.save(routes);
        routesRepo.save(routes1);
        routesRepo.save(routes2);

        List<RoutesModel> list  = routesRepo.getRoutesByStatus(statusSaved.getId());

        List<RoutesModel> list1  = routesRepo.getRoutesByStatus(statusSaved1.getId());

        Assertions.assertThat(list).size().isEqualTo(2);
        Assertions.assertThat(list).contains(routes, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getDeparturePoint()).isEqualTo("test3");
        Assertions.assertThat(list.get(0).getDestinationPoint()).isEqualTo("test3");

        Assertions.assertThat(list).contains(routes1, Index.atIndex(1));
        Assertions.assertThat(list.get(1).getDeparturePoint()).isEqualTo("test4");
        Assertions.assertThat(list.get(1).getDestinationPoint()).isEqualTo("test4");

        Assertions.assertThat(list1).size().isEqualTo(1);
        Assertions.assertThat(list1).contains(routes2, Index.atIndex(0));
        Assertions.assertThat(list1.get(0).getDeparturePoint()).isEqualTo("test5");
        Assertions.assertThat(list1.get(0).getDestinationPoint()).isEqualTo("test5");

    }

    @Test
    public void routesRepo_DeleteByID_ReturnRoutes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test3")
                .destinationPoint("test3")
                .status(statusSaved).build();

        RoutesModel routes1 = RoutesModel.builder()
                .departurePoint("test4")
                .destinationPoint("test4")
                .status(statusSaved).build();

        RoutesModel routesSave   = routesRepo.save(routes);
        RoutesModel routesSave1  = routesRepo.save(routes1);

        routesRepo.deleteById(routesSave.getId());
        List<RoutesModel> list  = routesRepo.findAll();

        Assertions.assertThat(list).size().isEqualTo(1);
        Assertions.assertThat(list).contains(routesSave1, Index.atIndex(0));
        Assertions.assertThat(list.get(0).getDestinationPoint()).isEqualTo("test4");

    }
}
