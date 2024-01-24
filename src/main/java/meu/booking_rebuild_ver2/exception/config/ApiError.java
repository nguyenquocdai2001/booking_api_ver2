package meu.booking_rebuild_ver2.exception.config;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
@Data
public class ApiError {

    private HttpStatus status;
    private String message;
    private boolean success;

    public ApiError(final HttpStatus status, final String message, final boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }
}
