package meu.booking_rebuild_ver2.exception.config;

import lombok.Data;
import meu.booking_rebuild_ver2.response.GenericResponse;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
@Data
public class ApiError {

    private HttpStatus status;
    private GenericResponse message;

    public ApiError(final HttpStatus status, final GenericResponse message) {
        this.status = status;
        this.message = message;
    }
}
