package com.gaboot.backend.common.exception;

import com.gaboot.backend.common.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto<Object>> handleBadRequestException(BadRequestException ex) {
        ResponseDto<Object> resp = new ResponseDto<>();
        System.out.println(ex.getMessage());
        resp.setMessage(ex.getMessage());
        resp.setSuccess(false);
        logger.info("Bad Request");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseDto<Object> resp = new ResponseDto<>();
        System.out.println(ex.getMessage());
        resp.setMessage(ex.getMessage());
        resp.setSuccess(false);
        logger.error("Resource not found: {}", ex.getMessage(), ex);
        logger.info("Resource not found");
        System.out.println("RESOURCE NOT FOUND HEY");
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    // Optional: Catch-all handler for other exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Object>> handleGeneralException(Exception ex) {
        System.out.println(ex.getMessage());
        ResponseDto<Object> resp = new ResponseDto<>();
        resp.setMessage(ex.getMessage());
        resp.setSuccess(false);
        logger.error("An error occurred: {}", ex.getMessage(), ex);
        logger.info("An error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}