package com.example.kanban.repository;

import com.example.kanban.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository <CardEntity, Long> {
}
