package com.example.springbootwebcrawler.controller;


import com.example.springbootwebcrawler.model.GenericResponse;
import com.example.springbootwebcrawler.model.ScanRequest;
import com.example.springbootwebcrawler.service.WebCrawlerService;
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


    private final WebCrawlerService service;

    @Autowired
    public MainController(WebCrawlerService service) {
        this.service = service;
    }

    @PostMapping("/scan")
    public ResponseEntity<GenericResponse> scan(@Valid @RequestBody ScanRequest scanRequest) throws IOException {
        log.info("getting info for: {}", scanRequest.getUrl());
        List<String> list = service.scan(scanRequest.getUrl(), scanRequest.getDomainOnly(), scanRequest.getBreakPoint());

        return ResponseEntity.ok(GenericResponse.builder()
                .message("Successfully scanned!")
                .data(list)
                .code(2000)
                .build());
    }


}
