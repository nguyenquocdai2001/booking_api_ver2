package meu.booking_rebuild_ver2.exception;

import meu.booking_rebuild_ver2.config.Constants;
import meu.booking_rebuild_ver2.response.GenericResponse;
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
    public ResponseEntity<GenericResponse> handleNotFoundException(){
        GenericResponse response = new GenericResponse(Constants.MESSAGE_GET_NOT_FOUND, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}