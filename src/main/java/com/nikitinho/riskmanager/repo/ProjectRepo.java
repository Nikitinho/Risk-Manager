package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
