package meu.booking_rebuild_ver2.response.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.TimeModel;
import meu.booking_rebuild_ver2.model.Status;

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


}
