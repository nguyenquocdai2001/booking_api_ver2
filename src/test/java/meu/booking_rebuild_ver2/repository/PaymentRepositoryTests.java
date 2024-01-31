package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.*;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.*;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.TestPropertySource;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentsRepo;
    @Autowired
    private PaymentTypeRepository paymentTypesRepo;
    @Autowired
    private StatusRepository statusRepo;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @Autowired
    private TimeRepository timeRepo;
    @Autowired
    private RoutesRepository routesRepo;
    @Autowired
    private RoutesTimeRepository routesTimeRepo;
    @Autowired
    private BusTypesRepository busTypesRepo;
    @Autowired
    private PriceRepository priceRepo;

    private UUID priceId ;
    private UUID customerId ;
    private UUID paymentTypeId ;
    @Test
    public void paymentsRepo_AddAndFindByID_ReturnPayment(){
        PaymentModel payments = createPayment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel paymentsModelSave = paymentsRepo.save(payments);

        Assertions.assertThat(paymentsModelSave).isNotNull();
        Assertions.assertThat(paymentsRepo.findPaymentModelById(paymentsModelSave.getId()).getTimePaid()).isEqualTo(timePaid);

    }

    @Test
    public void paymentsRepo_UpdatePayment_ReturnUpdatedPayment() {
        // Assuming paymentsModelSave is the PaymentModel saved in the previous test
        PaymentModel paymentsModelSave = createPayment(); // Create and save a PaymentModel for testing
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("21-02-2024 10:30", formatter);
        PaymentModel paymentsModelToUpdate = paymentsRepo.findPaymentModelById(paymentsModelSave.getId());
        paymentsModelToUpdate.setTimePaid(timePaid); // Updating the payments

        PaymentModel updatedPaymentModel = paymentsRepo.save(paymentsModelToUpdate);

        Assertions.assertThat(updatedPaymentModel).isNotNull();
        Assertions.assertThat(paymentsRepo.findPaymentModelById(paymentsModelSave.getId()).getTimePaid()).isEqualTo(timePaid);
    }
    @Test
    public void paymentsRepo_DeletePayment_SuccessfulDeletion() {
        // Assuming paymentsModelSave is the PaymentModel saved in the previous test
        PaymentModel paymentsModelSave = createPayment(); // Create and save a PaymentModel for testing

        // Deleting the PaymentModel
        paymentsRepo.deleteById(paymentsModelSave.getId());

        PaymentModel deleteModel = paymentsRepo.findPaymentModelById(paymentsModelSave.getId());
        Assertions.assertThat(deleteModel).isNull();

    }

    @Test
    public void paymentsRepo_GetByStatus_ReturnListPayment(){
        Status status = Status.builder().status("Disable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Status status1 = Status.builder().status("Enable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
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
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);

        LocalDateTime timePaid1 = LocalDateTime.parse("29-01-2024 16:30", formatter);
        PaymentModel payments1 = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid1)
                .status(statusSaved1).build();

        PaymentModel paymentsModelSave1 = paymentsRepo.save(payments1);

        List<PaymentModel> listStatus1  = paymentsRepo.getPaymentByStatus(statusSaved.getId());
        List<PaymentModel> listStatus2  = paymentsRepo.getPaymentByStatus(statusSaved1.getId());

        Assertions.assertThat(listStatus1).size().isEqualTo(1);
        Assertions.assertThat(listStatus1).contains(paymentsModelSave, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getTimePaid()).isEqualTo(timePaid);

        Assertions.assertThat(listStatus2).size().isEqualTo(1);
        Assertions.assertThat(listStatus2).contains(paymentsModelSave1, Index.atIndex(0));
        Assertions.assertThat(listStatus2.get(0).getTimePaid()).isEqualTo(timePaid1);

    }
    @Test
    public void paymentsRepo_GetByPrice_ReturnListPayment(){
        PaymentModel model = createPayment();
        Status status = Status.builder().status("Disable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty1")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1134567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("18-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test")
                .destinationPoint("test")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test ")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("160.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel priceModelSave = priceRepo.save(price);
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("29-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);

        LocalDateTime timePaid1 = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments1 = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid1)
                .status(statusSaved).build();

         paymentsRepo.save(payments1);

        List<PaymentModel> listStatus1  = paymentsRepo.getPaymentByPrice(priceModelSave.getId());
        List<PaymentModel> listStatus2  = paymentsRepo.getPaymentByPrice(priceId);

        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(paymentsModelSave, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getTimePaid()).isEqualTo(timePaid);

        Assertions.assertThat(listStatus2).size().isEqualTo(1);
        Assertions.assertThat(listStatus2).contains(model, Index.atIndex(0));
        Assertions.assertThat(listStatus2.get(0).getTimePaid()).isEqualTo(timePaid1);

    }

    @Test
    public void paymentsRepo_GetByCustomer_ReturnListPayment(){
        PaymentModel model = createPayment();
        Status status = Status.builder().status("Disable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty1")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1134567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("18-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test")
                .destinationPoint("test")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test ")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("160.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel priceModelSave = priceRepo.save(price);
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("29-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);

        LocalDateTime timePaid1 = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments1 = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid1)
                .status(statusSaved).build();

        paymentsRepo.save(payments1);

        List<PaymentModel> listStatus1  = paymentsRepo.getPaymentByCustomer(customerSaved1.getId());
        List<PaymentModel> listStatus2  = paymentsRepo.getPaymentByCustomer(customerId);

        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(paymentsModelSave, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getIdCustomer().getName()).isEqualTo("tesstasv");

        Assertions.assertThat(listStatus2).size().isEqualTo(1);
        Assertions.assertThat(listStatus2).contains(model, Index.atIndex(0));
        Assertions.assertThat(listStatus2.get(0).getIdCustomer().getPhone()).isEqualTo("1234567890");

    }
    @Test
    public void paymentsRepo_GetByPaymentType_ReturnListPayment(){
        PaymentModel model = createPayment();
        Status status = Status.builder().status("Disable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty1")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1134567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
        TimeModel time = TimeModel.builder()
                .startTime(Time.valueOf("04:30:00"))
                .endTime(Time.valueOf("04:30:00"))
                .startDate("18-01-2024")
                .endDate("23-01-2024")
                .status(statusSaved).build();
        TimeModel timeModelSaved = timeRepo.save(time);
        RoutesModel routes = RoutesModel.builder()
                .departurePoint("test")
                .destinationPoint("test")
                .status(statusSaved).build();
        RoutesModel routesModelSaved = routesRepo.save(routes);
        RoutesTimeModel routesTime = RoutesTimeModel.builder()
                .idRoutes(routesModelSaved)
                .idTime(timeModelSaved)
                .status(statusSaved).build();
        RoutesTimeModel routesTimeModel = routesTimeRepo.save(routesTime);

        BusTypes busTypes = BusTypes.builder()
                .type("test")
                .licensePlate("test ")
                .numberOfSeat(1)
                .status(statusSaved).build();
        BusTypes busTypesSave = busTypesRepo.save(busTypes);

        PriceModel price = PriceModel.builder()
                .price("160.000")
                .idBusType(busTypesSave)
                .idRoutesTime(routesTimeModel)
                .status(statusSaved).build();

        PriceModel priceModelSave = priceRepo.save(price);
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("29-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);

        LocalDateTime timePaid1 = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments1 = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid1)
                .status(statusSaved).build();

        paymentsRepo.save(payments1);

        List<PaymentModel> listStatus1  = paymentsRepo.getPaymentByPaymentTypes(paymentTypesModelSave.getId());
        List<PaymentModel> listStatus2  = paymentsRepo.getPaymentByPaymentTypes(paymentTypeId);

        Assertions.assertThat(listStatus1).size().isEqualTo(2);
        Assertions.assertThat(listStatus1).contains(paymentsModelSave, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getIdPaymentTypes().getPaymentType()).isEqualTo("MOMO");

        Assertions.assertThat(listStatus2).size().isEqualTo(1);
        Assertions.assertThat(listStatus2).contains(model, Index.atIndex(0));
        Assertions.assertThat(listStatus2.get(0).getIdPaymentTypes().getPaymentType()).isEqualTo("Banking");

    }
    private PaymentModel createPayment(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
        customerId =  customerSaved1.getId() ;
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
        priceId = priceModelSave.getId();
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("Banking")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);
        paymentTypeId = paymentTypesModelSave.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);
        return paymentsModelSave;
    }
    private PaymentModel createAnOtherPayment(){
        Status status = Status.builder().status("Disable").flag(true).build();
        Status statusSaved = statusRepo.save(status);
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
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
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime timePaid = LocalDateTime.parse("30-01-2024 10:30", formatter);
        PaymentModel payments = PaymentModel.builder()
                .idPaymentTypes(paymentTypesModelSave)
                .idPrice(priceModelSave)
                .idCustomer(customerSaved1)
                .timePaid(timePaid)
                .status(statusSaved).build();

        PaymentModel paymentsModelSave = paymentsRepo.save(payments);
        return paymentsModelSave;
    }
}
