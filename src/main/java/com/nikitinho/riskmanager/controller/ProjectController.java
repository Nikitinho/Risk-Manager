package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.Project;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.dto.EventType;
import com.nikitinho.riskmanager.dto.ObjectType;
import com.nikitinho.riskmanager.util.WsSender;
import com.nikitinho.riskmanager.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("project")
public class ProjectController {

    private final ProjectRepo projectRepo;
    private final BiConsumer<EventType, Project> wsSender;

    @Autowired
    public ProjectController(ProjectRepo projectRepo, WsSender wsSender) {
        this.projectRepo = projectRepo;
        this.wsSender = wsSender.getSender(ObjectType.PROJECT, Views.FullProject.class);
    }

    @GetMapping
    @JsonView(Views.FullProject.class)
    public List<Project> list() {
        return projectRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProject.class)
    public Project getOne(@PathVariable("id") Project project) {
        return project;
    }

    @PostMapping
    public Project create(@RequestBody Project project,
                          @AuthenticationPrincipal User user) {

        project.setCreationDate(LocalDateTime.now());
        project.setAuthor(user);
        Project uploadedProject = projectRepo.save(project);

        wsSender.accept(EventType.CREATE, uploadedProject);

        return uploadedProject;
    }

    @PutMapping("{id}")
    public Project update(@PathVariable("id") Project projectFromDb,
                       @RequestBody Project project) {

        BeanUtils.copyProperties(project, projectFromDb, "id");

        Project updatedProject = projectRepo.save(projectFromDb);

        wsSender.accept(EventType.UPDATE, updatedProject);

        return updatedProject;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Project project) {
        projectRepo.delete(project);

        wsSender.accept(EventType.REMOVE, project);
    }
}
