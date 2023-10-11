package com.example.kanban.controller;

import com.example.kanban.DTO.BoardDTO;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public BoardDTO getById(@PathVariable Long id) throws NotFoundException {
        return service.getBoardById(id);
    }

    @PostMapping("/")
    public BoardDTO create(@RequestBody BoardDTO board) throws NotFoundException {
        return service.createBoard(board);
    }

    @PutMapping("/{id}")
    public BoardDTO update(@PathVariable Long id, @RequestBody BoardDTO boardDTO) throws NotFoundException {
        return service.updateBoard(id, boardDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws NotFoundException {
        service.deleteBoard(id);
    }
}
