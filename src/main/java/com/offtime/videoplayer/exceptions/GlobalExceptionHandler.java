package com.offtime.videoplayer.exceptions;

import com.offtime.videoplayer.pojos.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUniqueIdException.class)
    public ResponseEntity<Response<Void>> handleInvalidUniqueId(InvalidUniqueIdException ex) {
        Response<Void> response = new Response<>(
            false,
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<Response<Void>> handleInvalidUser(InvalidUserException ex) {
        Response<Void> response = new Response<>(
            false,
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(InvalidVideoException.class)
    public ResponseEntity<Response<Void>> handleInvalidVideo(InvalidVideoException ex) {
        Response<Void> response = new Response<>(
            false,
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleGlobalException(Exception ex) {
        Response<Void> response = new Response<>(
            false,
            "Internal Server Error: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
