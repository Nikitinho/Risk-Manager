package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.Risk;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.repo.RiskRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("risk")
public class RiskController {
    private final RiskRepo riskRepo;

    @Autowired
    public RiskController(RiskRepo riskRepo) {
        this.riskRepo = riskRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Risk> list() {
        return riskRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullRisk.class)
    public Risk getOne(@PathVariable("id") Risk risk) {
        return risk;
    }

    @PostMapping
    public Risk create(@RequestBody Risk risk) {
        risk.setCreationDate(LocalDateTime.now());
        return riskRepo.save(risk);
    }

    @PutMapping("{id}")
    public Risk update(@PathVariable("id") Risk riskFromDb,
                                      @RequestBody Risk risk) {

        BeanUtils.copyProperties(risk, riskFromDb, "id");

        return riskRepo.save(riskFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Risk risk) {
        riskRepo.delete(risk);
    }

    @MessageMapping("/changeRisk")
    @SendTo("/topic/activity")
    public Risk change(Risk risk) throws Exception {
        return riskRepo.save(risk);
    }
}
