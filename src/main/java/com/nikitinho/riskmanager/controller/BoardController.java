package com.nikitinho.riskmanager.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nikitinho.riskmanager.domain.Board;
import com.nikitinho.riskmanager.domain.Views;
import com.nikitinho.riskmanager.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    @JsonView(Views.FullBoard.class)
    public List<Board> list() {
        return boardService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullBoard.class)
    public Board getOne(@PathVariable("id") Board board) {
        return board;
    }

    @PostMapping
    @JsonView(Views.FullBoard.class)
    public Board create(@RequestBody Board board) throws IOException {
        return boardService.create(board);
    }

    @PutMapping("{id}")
    @JsonView(Views.FullBoard.class)
    public Board update(@PathVariable("id") Board boardFromDb,
                              @RequestBody Board board) throws IOException {
        return boardService.update(boardFromDb, board);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Board board) {
        boardService.delete(board);
    }
}
