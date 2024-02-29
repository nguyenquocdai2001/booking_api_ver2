package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.BusSeat;
import meu.booking_rebuild_ver2.model.Admin.BusTypes;
import meu.booking_rebuild_ver2.model.Admin.DTO.BusSeatDTO;


import java.util.List;
import java.util.Optional;
/*
 * author: Nguyen Quoc Dai
 * ticket: BS-8
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusSeatResponse {
    private String message;
    private Boolean success;

    private List<BusSeatDTO> busSeatList;

    private Optional<BusSeatDTO> busSeat;

    private BusSeatDTO busSeatAdding;


    public BusSeatResponse(String message, Boolean success, List<BusSeatDTO> busSeatList) {
        this.message = message;
        this.success = success;
        this.busSeatList = busSeatList;
    }

    public BusSeatResponse(String message, Boolean success, BusSeatDTO busSeatAdding) {
        this.message = message;
        this.success = success;
        this.busSeatAdding = busSeatAdding;
    }

    public BusSeatResponse(String message, Boolean success, Optional<BusSeatDTO> busSeat) {
        this.message = message;
        this.success = success;
        this.busSeat = busSeat;
    }

    public BusSeatResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}
