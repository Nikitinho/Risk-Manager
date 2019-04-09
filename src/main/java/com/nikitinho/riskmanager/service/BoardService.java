package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.Board;
import com.nikitinho.riskmanager.repo.BoardRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
        private final BoardRepo boardRepo;

        @Autowired
        public BoardService(BoardRepo boardRepo) {
            this.boardRepo = boardRepo;
        }

        public void delete(Board board) {
            boardRepo.delete(board);
        }

        public Board update(Board boardFromDb, Board board) throws IOException {
            BeanUtils.copyProperties(board, boardFromDb, "id");

            Board updatedBoard = boardRepo.save(boardFromDb);

            return updatedBoard;
        }

        public Board create(Board board) throws IOException {
            board.setCreationDate(LocalDateTime.now());
            Board updatedBoard = boardRepo.save(board);

            return updatedBoard;
        }

        public List<Board> findAll() {
            return boardRepo.findAll();
        }
}
