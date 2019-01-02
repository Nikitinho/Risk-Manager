package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.Risk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRepo extends JpaRepository<Risk, Long> {
}
