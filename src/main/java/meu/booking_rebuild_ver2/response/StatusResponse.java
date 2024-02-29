package meu.booking_rebuild_ver2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import meu.booking_rebuild_ver2.model.Admin.DTO.StatusDTO;
import meu.booking_rebuild_ver2.model.Status;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * author: Nguyen Quoc Dai
 * ticket: BS-17
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse extends ResponseEntityExceptionHandler {
    private String message;
    private Boolean success;

    private List<StatusDTO> statusList;

    private StatusDTO status;


    public StatusResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public StatusResponse(String message, Boolean success, List<StatusDTO> statusList) {
        this.message = message;
        this.success = success;
        this.statusList = statusList;
    }


    public StatusResponse(String message, Boolean success, StatusDTO status) {
        this.message = message;
        this.success = success;
        this.status = status;
    }

    public StatusResponse(Boolean success, StatusDTO status) {
        this.success = success;
        this.status = status;
    }


}
