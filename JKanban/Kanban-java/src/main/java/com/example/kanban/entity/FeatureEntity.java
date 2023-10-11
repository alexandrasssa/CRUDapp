package com.example.kanban.entity;

import com.example.kanban.enums.FeatureCategory;
import com.example.kanban.enums.Status;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "features")
public class FeatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private FeatureCategory featureCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cardEntities = new ArrayList<>();

    public FeatureEntity() {
    }

    public FeatureEntity(String title, String description, FeatureCategory featureCategory, Status status, BoardEntity boardEntity) {
        this.title = title;
        this.description = description;
        this.featureCategory = featureCategory;
        this.status = status;
        this.boardEntity = boardEntity;
    }

    public List <CardEntity> getCards() {
        return cardEntities;
    }

    public void addCard(CardEntity cardEntity) {
        this.cardEntities.add(cardEntity);
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

    public FeatureCategory getFeatureCategory() {
        return featureCategory;
    }

    public void setFeatureCategory(FeatureCategory featureCategory) {
        this.featureCategory = featureCategory;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BoardEntity getBoardEntity() {
        return boardEntity;
    }

    public void setBoardEntity(BoardEntity boardEntity) {
        this.boardEntity = boardEntity;
    }

    public List<CardEntity> getCardEntities() {
        return cardEntities;
    }


    public void setCardEntities(List<CardEntity> cardEntities) {
        this.cardEntities = cardEntities;
    }
}
