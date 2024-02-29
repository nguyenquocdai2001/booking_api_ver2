package meu.booking_rebuild_ver2.repository;

import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.repository.Admin.LoyaltyRepository;
import meu.booking_rebuild_ver2.repository.Passanger.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
/*
author: Nguyen Minh Tam
test repo for bs-3
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class  CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @Autowired
    private StatusRepository statusRepository;
    // Function test to save
    @Test
    public void CustomerRepository_SavedAll_ReturnSavedCustomers() {
        int countPrev = (int) customerRepository.count();
        Loyalty loyalty = Loyalty.builder()
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);
        Customer customer1 = Customer.builder()
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer1);
        Customer customer2 = Customer.builder()
                .name("aedvdca")
                .phone("2223334445")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved2 = customerRepository.save(customer2);
        Assertions.assertNotNull(customerSaved1.getId());
        Assertions.assertNotNull(customerSaved2.getId());
        Assertions.assertNotEquals(customerSaved2, customerSaved1);
        Assertions.assertNotEquals("tesstasv", customerSaved2.getName());
        Assertions.assertNotEquals(customerSaved1.getId(), customerSaved2.getId());
        Assertions.assertEquals("2223334445", customerSaved2.getPhone());
        Assertions.assertEquals("1234567890", customerSaved1.getPhone());
        Assertions.assertEquals("tesstasv", customerSaved1.getName());
        Assertions.assertEquals(countPrev + 2, customerRepository.count());
    }
    // Function test save all and get all
    @Test
    public void CustomerRepository_SaveAllAndGetAll_ReturnListCustomer() {
        int countPrev = (int) customerRepository.count();
        Loyalty loyalty = Loyalty.builder()
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);
        Customer customer1 = Customer.builder()
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customer2 = Customer.builder()
                .name("aedvdca")
                .phone("2223334445")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerRepository.saveAll(customerList);
        List<Customer> listCustomers = customerRepository.findAll();
        Assertions.assertEquals(countPrev + 2, listCustomers.size());
        Assertions.assertNotEquals(listCustomers.get(0).getId(), listCustomers.get(1).getId());
    }
    // Function test delete
    @Test
    public void CustomerRepository_Delete_ReturnCustomer() {

        int countPrev = (int) customerRepository.count();

        Loyalty loyaltyToFind = Loyalty.builder()
                .rank("TestLoyaltyToFind")
                .discount(50)
                .loyaltySpent(1000)
                .build();
        Loyalty loyaltySaved2 = loyaltyRepository.save(loyaltyToFind);

        Status status2 = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved2 = statusRepository.save(status2);
        Customer customer2 = Customer.builder()
                .name("Customer1")
                .phone("1234567890")
                .loyalty(loyaltySaved2)
                .status(statusSaved2)
                .numberOfTrips(0)
                .build();

        Customer customerSaved = customerRepository.save(customer2);

        Customer modelDelete = customerRepository.findCustomerById(customerSaved.getId());

        Assertions.assertEquals(countPrev + 1, customerRepository.count());

        customerRepository.delete(modelDelete);

        List<Customer> listCustomers = customerRepository.findAll();

        Assertions.assertEquals(countPrev, listCustomers.size());
    }
    // FUnction test update
    @Test
    public void CustomerRepository_Update_ReturnCustomer(){
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltyNew = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyaltyUpdate")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Loyalty loyaltySaved1 = loyaltyRepository.save(loyaltyNew);
        Status status = Status.builder().status("TestStatus").flag(true).build();
        Status statusSaved = statusRepository.save(status);
        UUID idCustomer = UUID.randomUUID();
        Customer customer = Customer.builder()
                .id(idCustomer)
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer CustomerSaved = customerRepository.save(customer);

        Customer model = customerRepository.findCustomerById(customer.getId());
        model.setName("abc-def");
        model.setPhone("1111111111");
        model.setLoyalty(loyaltySaved1);
        model.setStatus(statusSaved);
        model.setNumberOfTrips(1);
        Customer modelUpdated =  customerRepository.save(model);
        Assertions.assertNotEquals(customer.getName(), modelUpdated.getName());
        Assertions.assertEquals(customer.getId(),modelUpdated.getId());
        Assertions.assertEquals("abc-def", modelUpdated.getName());
        Assertions.assertEquals("1111111111", modelUpdated.getPhone());
        Assertions.assertEquals(1, modelUpdated.getNumberOfTrips());
        Assertions.assertEquals("TestLoyaltyUpdate", modelUpdated.getLoyalty().getRank());
    }
    // FUnction test get by phone
    @Test
    public void CustomerRepository_GetByPhone_ReturnCustomer() {
        Loyalty loyalty = Loyalty.builder()
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);
        Customer customerToFind = Customer.builder()
                .name("CustomerToFind")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        customerRepository.save(customerToFind);

        Customer foundCustomer = customerRepository.findCustomerByPhone("1234567890");
        Assertions.assertNotNull(foundCustomer);
        Assertions.assertEquals(customerToFind.getId(), foundCustomer.getId());
    }
    // Function test get customer by loyalty
    @Test
    public void CustomerRepository_GetByLoyaltyId_ReturnCustomer() {
        Loyalty loyaltyToFind = Loyalty.builder()
                .rank("TestLoyaltyToFind")
                .discount(50)
                .loyaltySpent(1000)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyaltyToFind);

        Status status = Status.builder()
                .status("TestStatus")
                .flag(true)
                .build();
        Status statusSaved = statusRepository.save(status);

        Customer customer1 = Customer.builder()
                .name("Customer1")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        customerRepository.save(customer1);
        List<Customer> customersWithLoyalty = customerRepository.getCustomersByLoyalty_Id(loyaltySaved.getId());
        Assertions.assertEquals(1, customersWithLoyalty.size());
    }


}
