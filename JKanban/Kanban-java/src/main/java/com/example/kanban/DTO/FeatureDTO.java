package com.example.kanban.DTO;

import com.example.kanban.entity.CardEntity;
import com.example.kanban.entity.FeatureEntity;
import com.example.kanban.enums.FeatureCategory;
import com.example.kanban.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeatureDTO {
    private Long id;

    private String title;

    private String description;

    private FeatureCategory category;

    private Status status;

    private BoardDTO board;

    private List<CardDTO> tasks;


    public static FeatureDTO from(CardEntity cardEntity) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.id = cardEntity.getFeature().getId();
        featureDTO.title = cardEntity.getFeature().getTitle();
        featureDTO.description = cardEntity.getFeature().getDescription();
        featureDTO.category = cardEntity.getFeature().getFeatureCategory();
        featureDTO.status = cardEntity.getFeature().getStatus();
        featureDTO.board = BoardDTO.from(cardEntity.getFeature().getBoardEntity());
        return featureDTO;
    }

    public static FeatureDTO from(FeatureEntity featureEntity) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.id = featureEntity.getId();
        featureDTO.title = featureEntity.getTitle();
        featureDTO.description = featureEntity.getDescription();
        featureDTO.category = featureEntity.getFeatureCategory();
        featureDTO.status = featureEntity.getStatus();
        featureDTO.board = BoardDTO.from(featureEntity.getBoardEntity());
        featureDTO.tasks = featureEntity.getCards().stream().map(CardDTO::fromFeature).collect(Collectors.toList());
        return featureDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FeatureCategory getCategory() {
        return category;
    }

    public void setCategory(FeatureCategory category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public List<CardDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<CardDTO> tasks) {
        this.tasks = tasks;
    }
}
