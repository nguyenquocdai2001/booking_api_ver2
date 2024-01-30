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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Test
    public void CustomerRepository_SavedAll_ReturnSavedCustomers(){
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();
        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder().status("TestStatus").flag(true).build();
        Status statusSaved = statusRepository.save(status);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customerSaved1 = customerRepository.save(customer);
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("aedvdca")
                .phone("2223334445")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();

        Customer customerSaved2 = customerRepository.save(customer1);
        Assertions.assertNotEquals(customerSaved2,customerSaved1);
        Assertions.assertNotEquals("tesstasv", customerSaved2.getName());
        Assertions.assertNotEquals(customerSaved1.getId(), customerSaved2.getId());
        Assertions.assertEquals("2223334445", customerSaved2.getPhone());
        Assertions.assertEquals("1234567890", customerSaved1.getPhone());
        Assertions.assertEquals("tesstasv", customerSaved1.getName());
        Assertions.assertEquals(3, customerRepository.count());
    }
    @Test
    public void CustomerRepository_SaveAllAndGetAll_ReturnListCustomer(){
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();

        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder().status("TestStatus").flag(true).build();
        Status statusSaved = statusRepository.save(status);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("aedvdca")
                .phone("2223334445")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customer1);
         customerRepository.saveAll(customerList);
        List<Customer> listCustomers = customerRepository.findAll();
        Assertions.assertEquals(3, listCustomers.size());
        Assertions.assertNotEquals(listCustomers.get(0).getId(),listCustomers.get(1).getId());
        Assertions.assertNotEquals(listCustomers.get(2).getId(),listCustomers.get(1).getId());
        Assertions.assertNotEquals(listCustomers.get(0).getId(),listCustomers.get(2).getId());
    }
    @Test
    public void CustomerRepository_Delete_ReturnCustomer(){
        UUID id = UUID.fromString("226ad6eb-9996-4a11-8d8f-4d54cb86bd96");
        Loyalty loyalty = Loyalty.builder()
                .id(UUID.randomUUID())
                .rank("TestLoyalty")
                .discount(70)
                .loyaltySpent(1232.500)
                .build();

        Loyalty loyaltySaved = loyaltyRepository.save(loyalty);
        Status status = Status.builder().status("TestStatus").flag(true).build();
        Status statusSaved = statusRepository.save(status);
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name("tesstasv")
                .phone("1234567890")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .name("aedvdca")
                .phone("2223334445")
                .loyalty(loyaltySaved)
                .status(statusSaved)
                .numberOfTrips(0)
                .build();
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customer1);
        customerRepository.saveAll(customerList);
        Customer modelDelete = customerRepository.findCustomerById(id);
        Assertions.assertEquals(3,customerRepository.count());
        customerRepository.delete(modelDelete);
        List<Customer> listCustomers = customerRepository.findAll();
        Assertions.assertEquals(2, listCustomers.size());
    }
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
    @Test
    public void  CustomerRepository_GetByPhone_ReturnCustomer(){
        Customer model = customerRepository.findCustomerByPhone("0356013397");
        Assertions.assertEquals("226ad6eb-9996-4a11-8d8f-4d54cb86bd96", model.getId().toString());
    }
    @Test
    public void CustomerRepository_GetByLoyaltyId_ReturnCustomer(){
        List<Customer> model =  customerRepository.getCustomersByLoyalty_Id(UUID.fromString("c7c0f4ca-be7b-4b76-a98a-3b6e1d7f971d"));
        Assertions.assertEquals(1, model.size());
    }

}
