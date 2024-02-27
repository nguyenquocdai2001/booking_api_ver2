package meu.booking_rebuild_ver2.model.Admin.Mapper;

import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerTicketDTO;
import meu.booking_rebuild_ver2.model.Admin.CustomerTicketModel;
import meu.booking_rebuild_ver2.model.Status;
import meu.booking_rebuild_ver2.model.User;
/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
public class CustomerTicketMapper {
    public static CustomerTicketDTO customerTicketDTO(CustomerTicketModel customerTicketModel){
        CustomerTicketDTO customerTicketDTO = new CustomerTicketDTO();
        customerTicketDTO.setId(customerTicketModel.getId());
        customerTicketDTO.setPaid(customerTicketModel.isPaid());
        customerTicketDTO.setIdCustomer(customerTicketModel.getIdCustomer());
        customerTicketDTO.setIdPrice(customerTicketModel.getIdPrice());
        customerTicketDTO.setIdBusSeat(customerTicketModel.getIdBusSeat());
        customerTicketDTO.setDepartureLocation(customerTicketModel.getDepartureLocation());
        customerTicketDTO.setDestinationLocation(customerTicketModel.getDestinationLocation());
        customerTicketDTO.setIdStatus(customerTicketModel.getStatus().getId());
        customerTicketDTO.setIdUserConfig(customerTicketModel.getIdUserConfig().getId());
        customerTicketDTO.setTicketCode(customerTicketModel.getTicketCode());
        return customerTicketDTO;
    }

    public static CustomerTicketModel dtoToCustomerTickets(CustomerTicketDTO customerTicketDTO){
        CustomerTicketModel customerTicket = new CustomerTicketModel();
        customerTicket.setId(customerTicketDTO.getId());
        customerTicket.setIdPrice(customerTicketDTO.getIdPrice());
        customerTicket.setIdBusSeat(customerTicketDTO.getIdBusSeat());
        customerTicket.setIdCustomer(customerTicketDTO.getIdCustomer());
        customerTicket.setDepartureLocation(customerTicketDTO.getDepartureLocation());
        customerTicket.setDestinationLocation(customerTicketDTO.getDestinationLocation());
        customerTicket.setPaid(customerTicketDTO.isPaid());
        customerTicket.setTicketCode(customerTicketDTO.getTicketCode());
        customerTicket.setIdUserConfig(new User(customerTicketDTO.getIdUserConfig()));
        return customerTicket;
    }
}
