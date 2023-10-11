package com.example.kanban.repository;

import com.example.kanban.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository <BoardEntity, Long> {
}
