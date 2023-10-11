package com.example.kanban.service;

import com.example.kanban.DTO.BoardDTO;
import com.example.kanban.entity.BoardEntity;
import com.example.kanban.entity.UserEntity;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.UserRepository;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final BasicJsonParser parser = new BasicJsonParser();

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public BoardDTO getBoardById(Long id) throws NotFoundException {
        Optional<BoardEntity> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            throw new NotFoundException("Board not found with id: " + id);
        }

        return BoardDTO.from(board.get());
    }

    public BoardDTO createBoard(BoardDTO board) throws NotFoundException {
        Optional<UserEntity> user = userRepository.findById(board.getAuthorId());
        if (user.isEmpty()) {
            throw new NotFoundException("Author not found with id: " + board.getAuthorId());
        }
        UserEntity userEntityBd = user.get();
        BoardEntity boardEntity = new BoardEntity(board.getName(), userEntityBd);
        boardRepository.save(boardEntity);
        Example<BoardEntity> example = Example.of(boardEntity, ExampleMatcher.matching());
        return BoardDTO.from(boardRepository.findOne(example).orElseThrow());
    }

    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) throws NotFoundException {
        Optional<BoardEntity> boardEntity = boardRepository.findById(id);
        if (boardEntity.isEmpty()) {
            throw new NotFoundException("Board not found with id: " + id);
        }
        boardEntity.ifPresent(board -> {
            board.setName(boardDTO.getName());
        });
        return BoardDTO.from(boardRepository.save(boardEntity.get()));
    }

    public void deleteBoard(Long boardId) throws NotFoundException {
        BoardEntity boardEntity =
                boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));
        boardRepository.delete(boardEntity);
    }
}
