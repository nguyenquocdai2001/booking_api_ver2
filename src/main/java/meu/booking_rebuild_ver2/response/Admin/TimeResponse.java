
package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.TimeDTO;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeResponse {
    private String message;
    private Boolean success;

    private List<TimeDTO> timeDTOList;

    private TimeDTO timeDTO;

    public TimeResponse(String message, Boolean success, TimeDTO timeDTO) {
        this.message = message;
        this.success = success;
        this.timeDTO = timeDTO;
    }

    public TimeResponse(String message, Boolean success, List<TimeDTO> timeDTOList) {
        this.message = message;
        this.success = success;
        this.timeDTOList = timeDTOList;
    }

    public TimeResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}

