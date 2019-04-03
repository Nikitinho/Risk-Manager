package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.CRAMM;
import com.nikitinho.riskmanager.domain.Risk;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskRepo extends JpaRepository<Risk, Long> {

    // Greedy loading CRAMM for every Risk
    @EntityGraph(attributePaths = { "cramms" })
    List<Risk> findAll();
}
