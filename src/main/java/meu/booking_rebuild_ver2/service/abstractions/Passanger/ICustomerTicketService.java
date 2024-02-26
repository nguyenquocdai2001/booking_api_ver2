package meu.booking_rebuild_ver2.service.abstractions.Passanger;

import meu.booking_rebuild_ver2.model.Admin.DTO.CustomerTicketDTO;
import meu.booking_rebuild_ver2.response.Passanger.CustomerTicketResponse;

import java.util.UUID;

public interface ICustomerTicketService {
    CustomerTicketResponse createCustomerTicket(CustomerTicketDTO customerTicket);
    CustomerTicketResponse getAllCustomerTicket();
    CustomerTicketResponse updateCustomerTicket(CustomerTicketDTO customerTicket);
    CustomerTicketResponse findByID(UUID id);
    CustomerTicketResponse deleteById(UUID id);
    CustomerTicketResponse getCustomerTicketByStatus(UUID id);
    CustomerTicketResponse getCustomerTicketByTime(UUID id);
    CustomerTicketResponse getCustomerTicketByRoutes(UUID id);
    CustomerTicketResponse getCustomerTicketByRoutesTime(UUID id);
    CustomerTicketResponse getCustomerTicketByTicketCode(String ticketCode);
    CustomerTicketResponse getCustomerTicketByBusSeat(UUID id);
    CustomerTicketResponse getCustomerTicketByDeparture(UUID id);
    CustomerTicketResponse getCustomerTicketByDestination(UUID id);
    CustomerTicketResponse setPayForTicket(UUID id);

}
