package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.BoardItem;
import com.nikitinho.riskmanager.domain.User;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.service.BoardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("boarditem")
public class BoardItemController {
    private final BoardItemService boardItemService;

    @Autowired
    public BoardItemController(BoardItemService boardItemService) {
        this.boardItemService = boardItemService;
    }

    @GetMapping
    @JsonView(Views.FullBoardItem.class)
    public List<BoardItem> list() {
        return boardItemService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullBoardItem.class)
    public BoardItem getOne(@PathVariable("id") BoardItem boardItem) {
        return boardItem;
    }

    @PostMapping
    @JsonView(Views.FullBoardItem.class)
    public BoardItem create(@RequestBody BoardItem boardItem, @AuthenticationPrincipal User user) throws IOException {
        return boardItemService.create(boardItem, user);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullBoardItem.class)
    public BoardItem update(@PathVariable("id") BoardItem boardItemFromDb,
                        @RequestBody BoardItem boardItem) throws IOException {
        return boardItemService.update(boardItemFromDb, boardItem);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") BoardItem boardItem) {
        boardItemService.delete(boardItem);
    }
}
