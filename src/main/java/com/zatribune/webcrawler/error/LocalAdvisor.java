package com.zatribune.webcrawler.error;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zatribune.webcrawler.db.entities.Operation;
import com.zatribune.webcrawler.db.entities.OperationStatus;
import com.zatribune.webcrawler.model.GenericResponse;
import com.zatribune.webcrawler.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.MalformedURLException;

@Slf4j
@RestControllerAdvice
public class LocalAdvisor extends ResponseEntityExceptionHandler {


    private final OperationService operationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public LocalAdvisor(OperationService operationService) {
        this.operationService = operationService;
    }

    @ExceptionHandler(value = MalformedURLException.class)
    public ResponseEntity<GenericResponse> handleException(MalformedURLException ex, WebRequest request) {

        log.error("error {}", ex.getMessage());
        GenericResponse response = GenericResponse.builder()
                .message(ex.getMessage())
                .reason(ex.getCause() == null ? new String[]{ex.getLocalizedMessage()} : new String[]{ex.getCause().getMessage()})
                .code(4010)
                .build();

        handleSavingOperation(request);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = BackendException.class)
    public ResponseEntity<GenericResponse> handleException(BackendException ex, WebRequest request) {
        log.error("[INTERNAL ERROR] {}", ex.getMessage());
        GenericResponse response = GenericResponse.builder()
                .message(ex.getMessage())
                .reason(ex.getCause() == null ? new String[]{ex.getLocalizedMessage()} : new String[]{ex.getCause().getClass().getSimpleName()})
                .code(5010)
                .build();
        handleSavingOperation(request);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<GenericResponse> handleException(NotFoundException ex, WebRequest request) {
        log.error("error {}", ex.getMessage());
        GenericResponse response = GenericResponse.builder()
                .message(ex.getMessage())
                .code(4004)
                .build();
        handleSavingOperation(request);
        return ResponseEntity.badRequest().body(response);
    }


    public void handleSavingOperation(WebRequest request) {

        try {
            Operation operation = Operation.builder()
                    .info(mapper.writeValueAsString(request.getDescription(true)))
                    .status(OperationStatus.FAILURE)
                    .build();
            operationService.saveOrUpdate(operation);
        } catch (JsonProcessingException e) {
            log.error("error saving operation for request {}", e.getMessage());

        }
    }

}
