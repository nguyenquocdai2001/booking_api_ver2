package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.DriverDTO;
import java.util.List;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-11
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private String message;
    private Boolean success;
    private List<DriverDTO> driverDTOList;
    private DriverDTO driverDTO;

    public DriverResponse(String message, Boolean success){
        this.message = message;
        this.success = success;
    }

    public DriverResponse(String message, Boolean success, List<DriverDTO> driverDTOList){
        this.message = message;
        this.success = success;
        this.driverDTOList = driverDTOList;
    }

    public DriverResponse(String message, Boolean success, DriverDTO driverDTO){
        this.message = message;
        this.success = success;
        this.driverDTO = driverDTO;
    }
}
