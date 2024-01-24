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
        return new ResponseEntity<>("Access Denied (403 Forbidden)", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(GenericResponseExceptionHandler.class)
    public ResponseEntity<GenericResponse> handleGenericResponseException(GenericResponseExceptionHandler e){
        GenericResponse response = new GenericResponse(e.getMessage(), false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(){
        ApiError response = new ApiError(HttpStatus.NOT_FOUND, new GenericResponse(Constants.MESSAGE_GET_NOT_FOUND, false) );
        return buildResponseEntity(response);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        logger.error("error" + apiError.getMessage().getMessage());
        return new ResponseEntity<>(apiError.getMessage(), new HttpHeaders(), apiError.getStatus());
    }
}