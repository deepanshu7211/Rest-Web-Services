package com.deep.rest.webservices.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@RestController
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    //THis method is used for Java class Validation, it is pre-defined method, we are overriding it
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),"Validation Error ",
                ex.getBindingResult().toString());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        ExceptionResponse exceptionResponse1 = processFieldErrors(fieldErrors,exceptionResponse);

        return new ResponseEntity<>(exceptionResponse1,HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors,ExceptionResponse exceptionResponse ) {

        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            exceptionResponse.setDetails(fieldError.getField()+ fieldError.getDefaultMessage());
        }
        return exceptionResponse;
    }


}
