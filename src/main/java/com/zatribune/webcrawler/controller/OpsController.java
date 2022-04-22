package com.zatribune.webcrawler.controller;

import com.zatribune.webcrawler.db.entities.Operation;
import com.zatribune.webcrawler.model.GenericResponse;
import com.zatribune.webcrawler.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RequestMapping("/ops")
@RestController
public class OpsController {


    private final OperationService operationService;

    public OpsController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping(value = "/all")
    public GenericResponse getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String direction) {


        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sortBy));

        log.info("requesting Ops info");

        List<Operation>list=operationService.getAll(pageRequest);
        return GenericResponse.builder()
                .message("Success")
                .code(2000)
                .data(list)
                .build();
    }
}
