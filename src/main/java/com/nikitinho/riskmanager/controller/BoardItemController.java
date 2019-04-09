package com.nikitinho.riskmanager.controller;

import com.nikitinho.riskmanager.domain.BoardItem;
import com.nikitinho.riskmanager.service.BoardItemService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BoardItem> list() {
        return boardItemService.findAll();
    }

    @GetMapping("{id}")
    public BoardItem getOne(@PathVariable("id") BoardItem boardItem) {
        return boardItem;
    }

    @PostMapping
    public BoardItem create(@RequestBody BoardItem boardItem) throws IOException {
        return boardItemService.create(boardItem);
    }

    @PutMapping("{id}")
    public BoardItem update(@PathVariable("id") BoardItem boardItemFromDb,
                        @RequestBody BoardItem boardItem) throws IOException {
        return boardItemService.update(boardItemFromDb, boardItem);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") BoardItem boardItem) {
        boardItemService.delete(boardItem);
    }
}
