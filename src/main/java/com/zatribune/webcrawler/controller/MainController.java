package com.zatribune.webcrawler.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zatribune.webcrawler.db.entities.Operation;
import com.zatribune.webcrawler.db.entities.OperationStatus;
import com.zatribune.webcrawler.model.GenericResponse;
import com.zatribune.webcrawler.model.ScanRequest;
import com.zatribune.webcrawler.service.OperationService;
import com.zatribune.webcrawler.service.WebCrawlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/webcrawler")
@RestController
public class MainController {


    private final WebCrawlerService crawlerService;

    private final OperationService operationService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public MainController(WebCrawlerService crawlerService, OperationService operationService) {
        this.crawlerService = crawlerService;
        this.operationService = operationService;
    }

    @PostMapping("/scan")
    public ResponseEntity<GenericResponse> scan(@Valid @RequestBody ScanRequest scanRequest) throws IOException {
        log.info("getting info for: {}", scanRequest.getUrl());
        List<String> list = crawlerService.scan(scanRequest.getUrl(), scanRequest.getDomainOnly(), scanRequest.getBreakPoint());

        operationService.saveOrUpdate(Operation.builder()
                .info(mapper.writeValueAsString(scanRequest))
                .numOfResults(list.size())
                .status(OperationStatus.SUCCESS)
                .build()
        );
        return ResponseEntity.ok(GenericResponse.builder()
                .message("Successfully scanned!")
                .data(list)
                .code(2000)
                .build());
    }


}
