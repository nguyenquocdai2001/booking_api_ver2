package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Passanger.Location;
import meu.booking_rebuild_ver2.repository.Passanger.LocationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-4
 * */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class LocationRepositoryTest {
    @Autowired
    LocationRepository locationRepository;

    @Test
    public void LocationRepository_Save_ReturnLocationSaved(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();
        Location locationSaved = locationRepository.save(location);

        Assertions.assertThat(locationSaved).isNotNull();
        Assertions.assertThat(locationSaved.getId()).isNotNull();
        Assertions.assertThat(locationSaved.getStreet()).isEqualTo("nguyen huu tho");
    }

    @Test
    public void LocationRepository_FindAll_ReturnLocationList(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();

        Location location2 = Location.builder()
                .houseNumber("140")
                .street("dien bien phu")
                .ward("15")
                .district("binh thanh")
                .province("ho chi minh")
                .isDestination(false)
                .build();
        locationRepository.save(location);
        locationRepository.save(location2);

        List<Location> locationList = locationRepository.findAll();

        Assertions.assertThat(locationList).isNotNull();
        Assertions.assertThat(locationList.size()).isGreaterThan(0);
    }

    @Test
    public void LocationRepository_FindById_ReturnLocationById(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();

        Location location2 = Location.builder()
                .houseNumber("140")
                .street("dien bien phu")
                .ward("15")
                .district("binh thanh")
                .province("ho chi minh")
                .isDestination(false)
                .build();
        Location locationSaved = locationRepository.save(location);
        locationRepository.save(location2);

        Location locationById = locationRepository.findById(location.getId()).get();

        Assertions.assertThat(locationById).isNotNull();
        Assertions.assertThat(locationById.getWard()).isEqualTo(locationSaved.getWard());
    }

    @Test
    public void LocationRepository_FindAllLocationsByIsDestination_ReturnListLocationByIsDestination(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();

        Location location2 = Location.builder()
                .houseNumber("140")
                .street("dien bien phu")
                .ward("15")
                .district("binh thanh")
                .province("ho chi minh")
                .isDestination(false)
                .build();
        locationRepository.save(location);
        locationRepository.save(location2);

        List<Location> locations = locationRepository.findAllByDestinationIs(true);

        Assertions.assertThat(locations).isNotNull();
        Assertions.assertThat(locations.get(0).isDestination()).isEqualTo(location.isDestination());
        Assertions.assertThat(locations.size()).isGreaterThan(0);
    }

    @Test
    public void LocationRepository_FindAllLocationsByProvince_ReturnListLocationByProvince(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();

        Location location2 = Location.builder()
                .houseNumber("140")
                .street("dien bien phu")
                .ward("15")
                .district("binh thanh")
                .province("ho chi minh")
                .isDestination(false)
                .build();
        locationRepository.save(location);
        locationRepository.save(location2);

        List<Location> locations = locationRepository.findAllByProvince("ho chi minh");

        Assertions.assertThat(locations).isNotNull();
        Assertions.assertThat(locations.get(0).getProvince()).isEqualTo(location.getProvince());
        Assertions.assertThat(locations.size()).isGreaterThan(0);
    }

    @Test
    public void LocationRepository_UpdateLocation_ReturnLocationUpdated(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();
        locationRepository.save(location);

        Location locationSaved = locationRepository.findById(location.getId()).get();
        locationSaved.setProvince("ha noi");

        Location locationUpdated = locationRepository.save(locationSaved);

        Assertions.assertThat(locationUpdated).isNotNull();
        Assertions.assertThat(locationUpdated.getProvince()).isEqualTo("ha noi");
    }

    @Test
    public void LocationRepository_DeleteLocation_ReturnEmptyLocation(){
        Location location = Location.builder()
                .houseNumber("130")
                .street("nguyen huu tho")
                .ward("tan phong")
                .district("7")
                .province("ho chi minh")
                .isDestination(true)
                .build();
        Location locationSaved = locationRepository.save(location);
        locationRepository.deleteById(locationSaved.getId());

        Optional<Location> locationReturn = locationRepository.findById(locationSaved.getId());

        Assertions.assertThat(locationReturn).isEmpty();
    }
}
