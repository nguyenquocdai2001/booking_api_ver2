
package meu.booking_rebuild_ver2.response.Admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeResponse {
    private String message;
    private Boolean success;

    private List<TimeModel> timeModelList;

    private TimeModel timeModel;

    public TimeResponse(String message, Boolean success, TimeModel timeModel) {
        this.message = message;
        this.success = success;
        this.timeModel = timeModel;
    }

    public TimeResponse(String message, Boolean success, List<TimeModel> timeModelList) {
        this.message = message;
        this.success = success;
        this.timeModelList = timeModelList;
    }

    public TimeResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }
}

