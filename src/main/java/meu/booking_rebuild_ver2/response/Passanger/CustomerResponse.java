package meu.booking_rebuild_ver2.response.Passanger;

import lombok.*;
import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerDTO;
import meu.booking_rebuild_ver2.model.Admin.DTO.LoyaltyDTO;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
/*
Author: Nguyen Minh Tam
Response for controller of BS-3
 */
@Data
@NoArgsConstructor
public class CustomerResponse {
    @Setter(AccessLevel.NONE)
    private String message ;
    @Setter(AccessLevel.NONE)
    private boolean success;
    private CustomerDTO customer;
    private List<CustomerDTO>  listCustomer ;

    public CustomerResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public CustomerResponse(String message, boolean success, CustomerDTO customer) {
        this.message = message;
        this.success = success;
        this.customer = customer;
    }

    public CustomerResponse(String message, boolean success, List<CustomerDTO> listCustomer) {
        this.message = message;
        this.success = success;
        this.listCustomer = listCustomer;
    }

    public CustomerResponse(CustomerDTO customer) {
        this.customer = customer;
    }
}
