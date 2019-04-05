package com.nikitinho.riskmanager.controller;

import com.nikitinho.riskmanager.domain.Project;
import com.nikitinho.riskmanager.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("project")
public class ProjectController {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectController(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    @GetMapping
    public List<Project> list() {
        return projectRepo.findAll();
    }

    @GetMapping("{id}")
    public Project getOne(@PathVariable("id") Project project) {
        return project;
    }

    @PostMapping
    public Project create(@RequestBody Project project) {

        project.setCreationDate(LocalDateTime.now());
        Project uploadedProject = projectRepo.save(project);

        return uploadedProject;
    }

    @PutMapping("{id}")
    public Project update(@PathVariable("id") Project projectFromDb,
                       @RequestBody Project project) {

        BeanUtils.copyProperties(project, projectFromDb, "id");

        Project updatedProject = projectRepo.save(projectFromDb);

        return updatedProject;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Project project) {
        projectRepo.delete(project);
    }
}
