package org.example.shorturl.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> rootExHandler(RootException e){
        log.error("[rootExHandler] ex", e);
        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResult> exHandler(Exception e) {
        log.error("[exHandler] ex", e);
        ErrorResult errorResult = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
