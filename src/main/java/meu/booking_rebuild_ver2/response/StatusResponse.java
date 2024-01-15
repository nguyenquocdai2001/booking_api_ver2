package meu.booking_rebuild_ver2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    private String message;
    private Boolean success;

    private List<Status> statusList;

    private Status status;

    public StatusResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public StatusResponse(String message, Boolean success, List<Status> statusList) {
        this.message = message;
        this.success = success;
        this.statusList = statusList;
    }

    public StatusResponse(String message, Boolean success, Status status) {
        this.message = message;
        this.success = success;
        this.status = status;
    }

    public StatusResponse(Boolean success, Status status) {
        this.success = success;
        this.status = status;
    }


}
