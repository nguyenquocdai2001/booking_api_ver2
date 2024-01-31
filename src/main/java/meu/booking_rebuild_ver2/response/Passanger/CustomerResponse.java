package meu.booking_rebuild_ver2.response.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
@Data
@NoArgsConstructor
public class CustomerResponse {
    private CustomerDTO customer;
    private List<CustomerDTO>  listCustomer = new ArrayList<>();
    public CustomerResponse(CustomerDTO customer) {
        this.customer = customer;
    }

    public CustomerResponse(List<CustomerDTO> listCustomer) {
        this.listCustomer = listCustomer;
    }
}
