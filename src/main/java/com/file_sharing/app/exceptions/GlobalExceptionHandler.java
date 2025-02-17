package com.file_sharing.app.exceptions;

import com.file_sharing.app.dto.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //Handles ResourceNotFoundException and returns a custom response.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(
                ApiResponseMessage.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
    //Handles FileExceptions and returns a custom response.
    @ExceptionHandler(FileExceptions.class)
    public ResponseEntity<ApiResponseMessage> fileException(FileExceptions ex) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(
                ApiResponseMessage.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
    // Handles BadCredentialsException and returns a custom response
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseMessage> badCredentialsExceptionHandler(BadCredentialsException exception){
        logger.info("Execution Handler invoked !! Exception is {}",exception.getMessage());
        return new ResponseEntity<>(
                ApiResponseMessage.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .success(true)
                        .build(),HttpStatus.NOT_FOUND
        );
    }
}
