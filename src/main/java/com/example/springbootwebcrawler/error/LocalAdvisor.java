package com.example.springbootwebcrawler.error;


import com.example.springbootwebcrawler.model.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.MalformedURLException;

@Slf4j
@RestControllerAdvice
public class LocalAdvisor extends ResponseEntityExceptionHandler {



    @ExceptionHandler(value = MalformedURLException.class)
    public ResponseEntity<GenericResponse> handleException(MalformedURLException ex){

        log.error("error {}",ex.getMessage());
        GenericResponse response = GenericResponse.builder()
                .message(ex.getMessage())
                .reason(ex.getCause() == null ? new String[]{ex.getLocalizedMessage()} : new String[]{ex.getCause().getMessage()})
                .code(4010)
                .build();
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(value = BackendException.class)
    public ResponseEntity<GenericResponse> handleException(BackendException ex){
        log.error("error {}",ex.getMessage());
        GenericResponse response = GenericResponse.builder()
                .message(ex.getMessage())
                .reason(ex.getCause() == null ? new String[]{ex.getLocalizedMessage()} : new String[]{ex.getCause().getClass().getSimpleName()})
                .code(5010)
                .build();
        return ResponseEntity.internalServerError().body(response);
    }

}
