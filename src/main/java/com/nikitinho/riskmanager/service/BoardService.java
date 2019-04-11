package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.Board;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.dto.EventType;
import com.nikitinho.riskmanager.dto.ObjectType;
import com.nikitinho.riskmanager.repo.BoardRepo;
import com.nikitinho.riskmanager.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class BoardService {
        private final BoardRepo boardRepo;
        private final BiConsumer<EventType, Board> wsSender;

        @Autowired
        public BoardService(BoardRepo boardRepo, WsSender wsSender) {
            this.boardRepo = boardRepo;
            this.wsSender = wsSender.getSender(ObjectType.BOARD, Views.FullBoard.class);
        }

        public void delete(Board board) {
            wsSender.accept(EventType.REMOVE, board);
            boardRepo.delete(board);
        }

        public Board update(Board boardFromDb, Board board) throws IOException {
            BeanUtils.copyProperties(board, boardFromDb, "id");

            Board updatedBoard = boardRepo.save(boardFromDb);
            wsSender.accept(EventType.UPDATE, updatedBoard);

            return updatedBoard;
        }

        public Board create(Board board) throws IOException {
            board.setCreationDate(LocalDateTime.now());
            Board updatedBoard = boardRepo.save(board);
            wsSender.accept(EventType.CREATE, updatedBoard);

            return updatedBoard;
        }

        public List<Board> findAll() {
            return boardRepo.findAll();
        }
}
