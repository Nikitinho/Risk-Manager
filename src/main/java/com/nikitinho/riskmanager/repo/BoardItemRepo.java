package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardItemRepo extends JpaRepository<BoardItem, Long> {
}
