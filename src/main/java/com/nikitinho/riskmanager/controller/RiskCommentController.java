package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.RiskComment;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.service.RiskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("rcomment")
public class RiskCommentController {

    private final RiskCommentService riskCommentService;

    @Autowired
    public RiskCommentController(RiskCommentService riskCommentService) {
        this.riskCommentService = riskCommentService;
    }

    @GetMapping
    @JsonView(Views.FullRiskComment.class)
    public List<RiskComment> list() {
        return riskCommentService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullRiskComment.class)
    public RiskComment getOne(@PathVariable("id") RiskComment riskComment) {
        return riskComment;
    }

    @PostMapping
    @JsonView(Views.FullRiskComment.class)
    public RiskComment create(@RequestBody RiskComment riskComment,
                              @AuthenticationPrincipal User user) throws IOException {
        return riskCommentService.create(riskComment, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullRiskComment.class)
    public RiskComment update(@PathVariable("id") RiskComment riskCommentFromDb,
                       @RequestBody RiskComment riskComment) throws IOException {
        return riskCommentService.update(riskCommentFromDb, riskComment);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") RiskComment riskComment) {
        riskCommentService.delete(riskComment);
    }
}
