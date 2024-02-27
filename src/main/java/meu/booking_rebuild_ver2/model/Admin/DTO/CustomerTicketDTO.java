package meu.booking_rebuild_ver2.model.Admin.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.PaymentModel;
import meu.booking_rebuild_ver2.model.Admin.PriceModel;
import meu.booking_rebuild_ver2.model.Passanger.Customer;
import meu.booking_rebuild_ver2.model.Passanger.Location;
import java.util.UUID;
/*
 * author: Quoc Dat
 * ticket: BS-5
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTicketDTO {
    private UUID id;


    private PriceModel idPrice;

    private BusSeat idBusSeat;

    private Customer idCustomer;

    private Location departureLocation;

    private Location destinationLocation;

    private boolean isPaid;

    private String ticketCode;

    private UUID idStatus;

    private UUID idUserConfig;
}
