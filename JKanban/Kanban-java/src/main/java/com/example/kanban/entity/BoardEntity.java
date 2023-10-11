package com.example.kanban.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeatureEntity> featureEntities = new ArrayList<>();

    public BoardEntity() {
    }

    public BoardEntity(String name, UserEntity author) {
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public Long getAuthorId() {
        return author.getId();
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public List<FeatureEntity> getFeatureEntities() {
        return featureEntities;
    }

    public void setFeatureEntities(List<FeatureEntity> featureEntities) {
        this.featureEntities = featureEntities;
    }

    public void addFeature(FeatureEntity featureEntity) {
        this.featureEntities.add(featureEntity);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + getAuthorId() +
                ", features=" + getFeatureEntities() +
                '}';
    }

}
