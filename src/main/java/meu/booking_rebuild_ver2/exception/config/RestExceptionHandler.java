package meu.booking_rebuild_ver2.exception.config;

import meu.booking_rebuild_ver2.config.Constants;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import meu.booking_rebuild_ver2.exception.BadRequestException;
import meu.booking_rebuild_ver2.exception.BadCredentialsException;
import meu.booking_rebuild_ver2.exception.NotFoundException;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<Object> handleBadCredentials(final BadCredentialsException ex, final WebRequest request) {
        final ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), false);
        return buildResponseEntity(ex, apiError);
    }

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<Object> handleBadRequest(final BadRequestException ex, final WebRequest request) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), false);
        return buildResponseEntity(ex, apiError);
    }
    private ResponseEntity<Object> buildResponseEntity(Exception ex, ApiError apiError) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
