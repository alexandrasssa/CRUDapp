package com.example.kanban.DTO;

import com.example.kanban.entity.BoardEntity;

public class BoardDTO {

    private Long id;
    private String name;
    private Long authorId;

    public BoardDTO() {
    }

    public BoardDTO(Long id, String name, Long authorId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BoardDTO from(BoardEntity boardEntity) {
        return new BoardDTO(boardEntity.getId(), boardEntity.getName(), boardEntity.getAuthorId());
    }
}
