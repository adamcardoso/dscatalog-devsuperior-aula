package com.devsuperior.dscatalog.resources.exceptions;

import com.devsuperior.dscatalog.services.expections.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class) // This annotation is used to indicate that this method is a handler of exceptions
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now()); // Instant is a type that represents a moment in time
        err.setStatus(HttpStatus.NOT_FOUND.value()); // HttpStatus.NOT_FOUND.value() is the status code of the error
        err.setError("Resource not found"); // This is the error message
        err.setMessage(e.getMessage()); // This is the message of the exception
        err.setPath(request.getRequestURI()); // This is the path of the request

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // This is the response of the exception
    }
}
