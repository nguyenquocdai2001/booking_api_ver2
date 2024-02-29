package meu.booking_rebuild_ver2.model.Admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;


import java.util.UUID;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-8
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusSeatDTO {
    private UUID id;

    private boolean isAvailable;

    private String nameSeat;

    private int floorNumber;

    private BusTypeDTO idBusTypes;
}
