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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://27.74.255.96:5433/meu_booking_engine_v2",
        "spring.datasource.username=postgres",
        "spring.datasource.password=NSzo4uDp",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
public class PaymentTypesRepositoryTests {

    @Autowired
    private PaymentTypeRepository paymentTypesRepo;

    @Autowired
    private StatusRepository statusRepo;


    @Test
    public void paymentTypesRepo_AddAndFindByID_ReturnPaymentTypes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        
        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);

        Assertions.assertThat(paymentTypesModelSave).isNotNull();
        Assertions.assertThat(paymentTypesRepo.findPaymentTypesModelById(paymentTypesModelSave.getId()).getPaymentType()).isEqualTo("MOMO");

    }

    @Test
    public void paymentTypesRepo_UpdatePaymentTypes_ReturnUpdatedPaymentTypes() {
        // Assuming paymentTypesModelSave is the PaymentTypesModel saved in the previous test
        PaymentTypesModel paymentTypesModelSave = createPaymentTypes(); // Create and save a PaymentTypesModel for testing

        PaymentTypesModel paymentTypesModelToUpdate = paymentTypesRepo.findPaymentTypesModelById(paymentTypesModelSave.getId());
        paymentTypesModelToUpdate.setPaymentType("Banking"); // Updating the paymentTypes

        PaymentTypesModel updatedPaymentTypesModel = paymentTypesRepo.save(paymentTypesModelToUpdate);

        Assertions.assertThat(updatedPaymentTypesModel).isNotNull();
        Assertions.assertThat(updatedPaymentTypesModel.getPaymentType()).isEqualTo("Banking");
    }
    @Test
    public void paymentTypesRepo_DeletePaymentTypes_SuccessfulDeletion() {
        // Assuming paymentTypesModelSave is the PaymentTypesModel saved in the previous test
        PaymentTypesModel paymentTypesModelSave = createPaymentTypes(); // Create and save a PaymentTypesModel for testing

        // Deleting the PaymentTypesModel
        paymentTypesRepo.deleteById(paymentTypesModelSave.getId());

        PaymentTypesModel deleteModel = paymentTypesRepo.findPaymentTypesModelById(paymentTypesModelSave.getId());
        Assertions.assertThat(deleteModel).isNull();

    }

    @Test
    public void paymentTypesRepo_GetByStatus_ReturnListPaymentTypes(){
        List<PaymentTypesModel> list = new ArrayList<>();
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);

        Status status1 = Status.builder().status("Disable").flag(true).build();
        Status statusSaved1 = statusRepo.save(status1);

        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypes1 = PaymentTypesModel.builder()
                .paymentType("Banking")
                .status(statusSaved1).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);
        PaymentTypesModel paymentTypesModelSave1 = paymentTypesRepo.save(paymentTypes1);
        list.add(paymentTypesModelSave);
        list.add(paymentTypesModelSave1);

        List<PaymentTypesModel> listStatus1  = paymentTypesRepo.getPaymentTypesByStatus(statusSaved.getId());
        List<PaymentTypesModel> listStatus2  = paymentTypesRepo.getPaymentTypesByStatus(statusSaved1.getId());

        Assertions.assertThat(listStatus1).size().isEqualTo(1);
        Assertions.assertThat(listStatus1).contains(paymentTypesModelSave, Index.atIndex(0));
        Assertions.assertThat(listStatus1.get(0).getPaymentType()).isEqualTo("MOMO");

        Assertions.assertThat(listStatus2).size().isEqualTo(1);
        Assertions.assertThat(listStatus2).contains(paymentTypesModelSave1, Index.atIndex(0));
        Assertions.assertThat(listStatus2.get(0).getPaymentType()).isEqualTo("Banking");

    }

    private PaymentTypesModel createPaymentTypes(){
        Status status = Status.builder().status("Enable").flag(true).build();
        Status statusSaved = statusRepo.save(status);


        PaymentTypesModel paymentTypes = PaymentTypesModel.builder()
                .paymentType("MOMO")
                .status(statusSaved).build();

        PaymentTypesModel paymentTypesModelSave = paymentTypesRepo.save(paymentTypes);
        return paymentTypesModelSave;
    }
}
