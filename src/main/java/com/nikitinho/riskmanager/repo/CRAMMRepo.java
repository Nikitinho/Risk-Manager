package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.CRAMM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CRAMMRepo extends JpaRepository<CRAMM, Long> {
}
