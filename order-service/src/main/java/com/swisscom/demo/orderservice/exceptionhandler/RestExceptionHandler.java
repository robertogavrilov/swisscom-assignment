package com.swisscom.demo.orderservice.exceptionhandler;

import com.swisscom.demo.orderservice.apierror.ApiError;
import com.swisscom.demo.orderservice.exceptions.BadRequestException;
import com.swisscom.demo.orderservice.exceptions.ConflictException;
import com.swisscom.demo.orderservice.exceptions.RecordNotFoundException;
import com.swisscom.demo.orderservice.exceptions.ServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            RecordNotFoundException ex) {
        return handleException(NOT_FOUND, ex);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(
            BadRequestException ex) {
        return handleException(BAD_REQUEST, ex);
    }

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleServiceException(
            ServiceException ex) {
        return handleException(SERVICE_UNAVAILABLE, ex);
    }

    private ResponseEntity<Object> handleException(HttpStatus status, Exception ex) {
        ApiError apiError = new ApiError(status);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}