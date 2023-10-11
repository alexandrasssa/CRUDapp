package com.example.kanban.DTO;

import com.example.kanban.entity.CardEntity;
import com.example.kanban.enums.Status;

public class CardDTO {

    private Long id;

    private Long featureId;
    private String title;
    private String description;
    private Status status;

    private FeatureDTO featureDTO;

    public CardDTO() {
    }

    public CardDTO(Long id, String title, String description, Status status, FeatureDTO featureDTO) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.featureDTO = featureDTO;
    }

    public CardDTO(Long id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    static public CardDTO from(CardEntity cardEntity) {
        return new CardDTO(cardEntity.getId(), cardEntity.getTitle(), cardEntity.getDescription(),
                cardEntity.getStatus(), FeatureDTO.from(cardEntity));
    }

    static public CardDTO fromFeature(CardEntity cardEntity) {
        return new CardDTO(cardEntity.getId(), cardEntity.getTitle(), cardEntity.getDescription(), cardEntity.getStatus());
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public FeatureDTO getFeatureDTO() {
        return featureDTO;
    }

    public void setFeatureDTO(FeatureDTO featureDTO) {
        this.featureDTO = featureDTO;
    }

    public Long getFeatureId() {
        return featureId != null ? featureId : featureDTO != null ? featureDTO.getId() : null;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }
}
