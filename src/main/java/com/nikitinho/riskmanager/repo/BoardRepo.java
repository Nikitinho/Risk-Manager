package com.nikitinho.riskmanager.repo;

import com.nikitinho.riskmanager.domain.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepo extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = { "items" })
    List<Board> findAll();
}
