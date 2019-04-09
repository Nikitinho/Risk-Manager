package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.RiskComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskCommentRepo extends JpaRepository<RiskComment, Long> {
}
