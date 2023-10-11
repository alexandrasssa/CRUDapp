package com.example.kanban.service;

import com.example.kanban.DTO.CardDTO;
import com.example.kanban.entity.CardEntity;
import com.example.kanban.entity.FeatureEntity;
import com.example.kanban.enums.Status;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.repository.CardRepository;
import com.example.kanban.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;

    private final FeatureRepository featureRepository;
    private final BasicJsonParser parser = new BasicJsonParser();

    @Autowired
    public CardService(CardRepository repository, FeatureRepository featureRepository) {
        this.cardRepository = repository;
        this.featureRepository = featureRepository;
    }

    public List<CardDTO> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardDTO::from)
                .collect(Collectors.toList());
    }

    public CardDTO getCardById(Long id) throws NotFoundException {
        Optional<CardEntity> card = cardRepository.findById(id);
        if (card.isPresent()) {
            return CardDTO.from(card.get());
        } else {
            throw new NotFoundException("Card not found with id: " + id);
        }
    }

    public List<CardDTO> getCardsByFeatureId(Long featureId) throws NotFoundException {
        Optional<FeatureEntity> list = featureRepository.findById(featureId);
        if (list.isEmpty()) {
            throw new NotFoundException("Board not found with id: " + featureId);
        }
        return list.get().getCards().stream().map(CardDTO::from).collect(Collectors.toList());
    }

    public CardDTO createCard(CardDTO card) throws NotFoundException {
        Optional<FeatureEntity> featureEntityOptional = featureRepository.findById(card.getFeatureId());
        if (featureEntityOptional.isEmpty()) {
            throw new NotFoundException("Feature not found with id: " + card.getFeatureId());
        }
        FeatureEntity featureEntity = featureEntityOptional.get();
        CardEntity cardEntity = new CardEntity(card.getTitle(), card.getDescription(), featureEntity);
        featureEntity.addCard(cardEntity);
        featureEntity.setStatus(featureEntity.getCardEntities().stream().map(CardEntity::getStatus).min(Comparator.naturalOrder()).orElseThrow());
        cardRepository.save(cardEntity);
        featureRepository.save(featureEntity);

        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<CardEntity> example = Example.of(cardEntity, matcher);
        Optional<CardEntity> foundCard = cardRepository.findOne(example);
        if (foundCard.isPresent()) {
            return CardDTO.from(foundCard.get());
        } else {
            throw new NotFoundException("Card was not found");
        }
    }

    public CardDTO updateCard(Long id, String attributes) throws NotFoundException {
        CardEntity cardEntity =
                cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot update card that doesn't exist"));
        Long oldFeatureId = cardEntity.getFeature().getId();
        Map<String, Object> map = parser.parseMap(attributes);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String s = entry.getKey();
            Object o = entry.getValue();
            switch (s) {
                case "title" -> {
                    cardEntity.setTitle(o.toString());
                }
                case "description" -> {
                    cardEntity.setDescription(o.toString());
                }
                case "status" -> {
                    cardEntity.setStatus(Status.valueOf(o.toString()));
                }
                case "featureId" -> {
                    FeatureEntity featureEntity = featureRepository
                            .findById(Long.parseLong(o.toString())).orElseThrow(() -> new NotFoundException("Cannot update card's feature that doesn't exist"));
                    cardEntity.setFeature(featureEntity);
                }
            }
        }

        CardEntity updatedEntity = cardRepository.save(cardEntity);
        FeatureEntity featureEntity = cardEntity.getFeature();
        featureEntity.setStatus(featureEntity.getCardEntities().stream().map(CardEntity::getStatus).min(Comparator.naturalOrder()).orElseThrow());
        featureRepository.save(featureEntity);

        if (!oldFeatureId.equals(featureEntity.getId())) {
            updateOldFeatureStatus(oldFeatureId);
        }

        return CardDTO.from(updatedEntity);
    }

    private void updateOldFeatureStatus(Long oldFeatureId) {
        Optional<FeatureEntity> oldFeatureEntityOptional = featureRepository.findById(oldFeatureId);
        if (oldFeatureEntityOptional.isEmpty()) {
            return;
        }
        FeatureEntity featureEntity = oldFeatureEntityOptional.get();
        featureEntity.setStatus(featureEntity.getCardEntities().stream()
                .map(CardEntity::getStatus).min(Comparator.naturalOrder()).orElse(Status.BACKLOG));
        featureRepository.save(featureEntity);
    }

    public void deleteCard(Long id) {
        Optional<CardEntity> card = cardRepository.findById(id);
        card.ifPresent(cardRepository::delete);
    }
}
