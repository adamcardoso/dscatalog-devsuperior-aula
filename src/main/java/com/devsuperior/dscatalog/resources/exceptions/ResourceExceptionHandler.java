package com.devsuperior.dscatalog.resources.exceptions;

import com.devsuperior.dscatalog.services.expections.DatabaseException;
import com.devsuperior.dscatalog.services.expections.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // This annotation is used to indicate that this method is a handler of exceptions
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now()); // Instant is a type that represents a moment in time
        err.setStatus(status.value()); // HttpStatus.NOT_FOUND.value() is the status code of the error
        err.setError("Resource not found"); // This is the error message
        err.setMessage(e.getMessage()); // This is the message of the exception
        err.setPath(request.getRequestURI()); // This is the path of the request

        return ResponseEntity.status(status).body(err); // This is the response of the exception
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Database exception");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
