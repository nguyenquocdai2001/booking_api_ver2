package meu.booking_rebuild_ver2.response.Passanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerTicketDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTicketResponse {
    private String message;
    private Boolean success;

    private List<CustomerTicketDTO> customerTicketDTOList;

    private CustomerTicketDTO customerTicketDTO;


    public CustomerTicketResponse(String message, Boolean success, CustomerTicketDTO customerTicketDTO) {
        this.message = message;
        this.success = success;
        this.customerTicketDTO = customerTicketDTO;
    }

    public CustomerTicketResponse(String message, Boolean success, List<CustomerTicketDTO> customerTicketDTOList) {
        this.message = message;
        this.success = success;
        this.customerTicketDTOList = customerTicketDTOList;
    }

    public CustomerTicketResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
