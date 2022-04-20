package com.example.springbootwebcrawler.controller;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScanRequest {

    @NotNull
    @NotBlank
    private String url;
    private Integer breakPoint;
    private Boolean domainOnly;
}
