package com.example.main.exception;

import com.example.main.dto.ResponseDTO;
import com.example.main.util.APICode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        ResponseDTO response = new ResponseDTO(APICode.FAILED, ex.getMessage());
        log.info("Request Failed :{}, {}", ex.getHttpStatus().value(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            errors.put(e.getField(), e.getDefaultMessage());
        });

        ResponseDTO response = new ResponseDTO(APICode.FAILED, errors);
        log.info("Request Failed :{}, {}", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ResponseDTO response = new ResponseDTO(APICode.FAILED, "Request body is missing or malformed");
        log.info("Request Failed :{}, {}", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // Catch ALL other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        ResponseDTO response = new ResponseDTO(APICode.FAILED, "Something went wrong. Please try again later.");
        log.info("Request Failed :{}, {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
