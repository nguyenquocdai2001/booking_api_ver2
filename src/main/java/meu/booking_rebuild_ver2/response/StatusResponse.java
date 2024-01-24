package meu.booking_rebuild_ver2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse extends ResponseEntityExceptionHandler {
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
