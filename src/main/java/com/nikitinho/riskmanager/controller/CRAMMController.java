package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.CRAMM;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.service.CRAMMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cramm")
public class CRAMMController {
    private final CRAMMService crammService;

    @Autowired
    public CRAMMController(CRAMMService crammService) {
        this.crammService = crammService;
    }

    @PostMapping
    @JsonView(Views.FullRisk.class)
    public CRAMM create(@RequestBody CRAMM cramm) {
        return crammService.create(cramm);
    }
}
