package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.Project;
import com.nikitinho.riskmanager.domain.Risk;
import com.nikitinho.riskmanager.domain.RiskStatusType;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.dto.EventType;
import com.nikitinho.riskmanager.dto.ObjectType;
import com.nikitinho.riskmanager.repo.RiskRepo;
import com.nikitinho.riskmanager.service.NotificationService;
import com.nikitinho.riskmanager.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("risk")
public class RiskController {
    private final RiskRepo riskRepo;
    private final BiConsumer<EventType, Risk> wsSender;
    private final NotificationService notificationService;

    @Autowired
    public RiskController(RiskRepo riskRepo, WsSender wsSender, NotificationService notificationService) {
        this.riskRepo = riskRepo;
        this.wsSender = wsSender.getSender(ObjectType.RISK, Views.FullRisk.class);
        this.notificationService = notificationService;
    }

    @GetMapping
    @JsonView(Views.FullRisk.class)
    public List<Risk> list() {
        return riskRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullRisk.class)
    public Risk getOne(@PathVariable("id") Risk risk) {
        return risk;
    }

    @PostMapping
    @JsonView(Views.FullRisk.class)
    public Risk create(@RequestBody Risk risk) {

        risk.setCreationDate(LocalDateTime.now());
        risk.setStatus(RiskStatusType.CREATED.toString());

        Risk uploadedRisk = riskRepo.save(risk);
        Runnable runnable = () -> {
            notificationService.sendNotification(uploadedRisk, EventType.CREATE);
            wsSender.accept(EventType.CREATE, uploadedRisk);
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return uploadedRisk;
    }

    @PutMapping("{id}")
    @JsonView(Views.FullRisk.class)
    public Risk update(@PathVariable("id") Risk riskFromDb,
                                      @RequestBody Risk risk) {

        BeanUtils.copyProperties(risk, riskFromDb, "id");
        riskFromDb.setStatus(RiskStatusType.fromString(risk.getStatus().toString()).toString());

        Risk updatedRisk = riskRepo.save(riskFromDb);

        Runnable runnable = () -> {
            notificationService.sendNotification(updatedRisk, EventType.UPDATE);
            wsSender.accept(EventType.UPDATE, updatedRisk);
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return updatedRisk;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Risk risk) {
        wsSender.accept(EventType.REMOVE, risk);
        riskRepo.delete(risk);
//        notificationService.sendNotification(risk, EventType.REMOVE);
    }

}
