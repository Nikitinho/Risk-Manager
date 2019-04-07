package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.Project;
import com.nikitinho.riskmanager.domain.Risk;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    @EntityGraph(attributePaths = { "risks" })
    List<Project> findAll();
}
