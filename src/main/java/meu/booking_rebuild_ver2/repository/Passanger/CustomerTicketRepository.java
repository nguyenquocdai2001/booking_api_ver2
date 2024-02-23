
package meu.booking_rebuild_ver2.repository.Passanger;

import meu.booking_rebuild_ver2.model.Admin.CustomerTicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
@Repository
public interface CustomerTicketRepository extends JpaRepository<CustomerTicketModel, UUID> {
    CustomerTicketModel findCustomerTicketModelById(UUID id);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.ticketCode = :ticketCode")
    CustomerTicketModel getCustomerTicketByTicketCode(@Param("ticketCode") String ticketCode);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.idBusSeat.id = :busSeatId")
    CustomerTicketModel getCustomerTicketByBusSeat(@Param("busSeatId") UUID busSeatId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.status.id = :statusId")
    List<CustomerTicketModel> getCustomerTicketByStatus(@Param("statusId") UUID statusId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.idPrice.idRoutesTime.id = :routesTimeId")
    List<CustomerTicketModel> getCustomerTicketByRouteTime(@Param("routesTimeId") UUID routesTimeId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.idPrice.idRoutesTime.idRoutes.id = :routesId")
    List<CustomerTicketModel> getCustomerTicketByRoute(@Param("routesId") UUID routesId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.idPrice.idRoutesTime.idTime.id = :timeId")
    List<CustomerTicketModel> getCustomerTicketByTime(@Param("timeId") UUID timeId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.departureLocation.id = :departureLocationId")
    List<CustomerTicketModel> getCustomerTicketByDepartureLocation(@Param("departureLocationId") UUID departureLocationId);
    @Query(value = "SELECT T FROM CustomerTicketModel T WHERE T.destinationLocation.id = :destinationLocationId")
    List<CustomerTicketModel> getCustomerTicketByDestinationLocation(@Param("destinationLocationId") UUID destinationLocationId);
}

