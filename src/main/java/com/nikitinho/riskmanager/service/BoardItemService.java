package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.BoardItem;
import com.nikitinho.riskmanager.repo.BoardItemRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardItemService {
    private final BoardItemRepo boardItemRepo;

    @Autowired
    public BoardItemService(BoardItemRepo boardItemRepo) {
        this.boardItemRepo = boardItemRepo;
    }

    public void delete(BoardItem boardItem) {
        boardItemRepo.delete(boardItem);
    }

    public BoardItem update(BoardItem boardItemFromDb, BoardItem boardItem) throws IOException {
        BeanUtils.copyProperties(boardItem, boardItemFromDb, "id");

        BoardItem updatedBoardItem = boardItemRepo.save(boardItemFromDb);

        return updatedBoardItem;
    }

    public BoardItem create(BoardItem boardItem) throws IOException {
        boardItem.setCreationDate(LocalDateTime.now());
        BoardItem updatedBoardItem = boardItemRepo.save(boardItem);

        return updatedBoardItem;
    }

    public List<BoardItem> findAll() {
        return boardItemRepo.findAll();
    }
}
