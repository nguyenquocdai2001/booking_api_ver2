package meu.booking_rebuild_ver2.exception;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.exception.config.ApiError;
import meu.booking_rebuild_ver2.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Access Denied (403 Forbidden)", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(GenericResponseExceptionHandler.class)
    public ResponseEntity<Object> handleGenericResponseException(GenericResponseExceptionHandler e){
        e.printStackTrace();
        ApiError response = new ApiError(HttpStatus.BAD_REQUEST, new GenericResponse(e.getMessage(), false));
        return buildResponseEntity(response);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        e.printStackTrace();
        ApiError response = new ApiError(HttpStatus.NOT_FOUND, new GenericResponse(e.getLocalizedMessage(), false) );
        return buildResponseEntity(response);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        logger.error("error " + apiError.getMessage().getMessage());
        return new ResponseEntity<>(apiError.getMessage(), new HttpHeaders(), apiError.getStatus());
    }
}