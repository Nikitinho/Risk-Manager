package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.BoardItem;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.dto.EventType;
import com.nikitinho.riskmanager.dto.ObjectType;
import com.nikitinho.riskmanager.repo.BoardItemRepo;
import com.nikitinho.riskmanager.util.WsSender;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class BoardItemService {
    private final BoardItemRepo boardItemRepo;
    private final BiConsumer<EventType, BoardItem> wsSender;

    @Autowired
    public BoardItemService(BoardItemRepo boardItemRepo, WsSender wsSender) {
        this.boardItemRepo = boardItemRepo;
        this.wsSender = wsSender.getSender(ObjectType.BOARD_ITEM, Views.FullBoardItem.class);
    }

    public void delete(BoardItem boardItem) {
        wsSender.accept(EventType.REMOVE, boardItem);
        boardItemRepo.delete(boardItem);
    }

    public BoardItem update(BoardItem boardItemFromDb, BoardItem boardItem) throws IOException {
        BeanUtils.copyProperties(boardItem, boardItemFromDb, "id");

        BoardItem updatedBoardItem = boardItemRepo.save(boardItemFromDb);

        wsSender.accept(EventType.UPDATE, updatedBoardItem);

        return updatedBoardItem;
    }

    public BoardItem create(BoardItem boardItem, User user) throws IOException {
        boardItem.setCreationDate(LocalDateTime.now());
        boardItem.setAuthor(user);
        BoardItem updatedBoardItem = boardItemRepo.save(boardItem);

        wsSender.accept(EventType.CREATE, updatedBoardItem);

        return updatedBoardItem;
    }

    public List<BoardItem> findAll() {
        return boardItemRepo.findAll();
    }
}
