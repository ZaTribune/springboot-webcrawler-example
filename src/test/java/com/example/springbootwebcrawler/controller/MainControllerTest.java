package com.example.springbootwebcrawler.controller;


import com.example.springbootwebcrawler.model.ScanRequest;
import com.example.springbootwebcrawler.service.WebCrawlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MainController.class)
class MainControllerTest {


    @MockBean
    private WebCrawlerService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void whenScanWithValidInput_thenReturnSuccessfully() throws Exception {

        String url = "https://www.google.com/";

        ObjectMapper objectMapper=new ObjectMapper();

        ScanRequest scanRequest= ScanRequest.builder()
                .url(url)
                .breakPoint(100)
                .domainOnly(true)
                .build();


        System.out.println(objectMapper.writeValueAsString(scanRequest));

        mockMvc.perform(post("/webcrawler/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scanRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    void whenScanWithInValidInput_thenThrowException() throws Exception {

        ObjectMapper objectMapper=new ObjectMapper();

        ScanRequest scanRequest= ScanRequest.builder()
                .url(null)
                .breakPoint(100)
                .domainOnly(true)
                .build();


        System.out.println(objectMapper.writeValueAsString(scanRequest));

        mockMvc.perform(post("/webcrawler/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scanRequest))
                )
                .andExpect(status().is4xxClientError());
    }
}