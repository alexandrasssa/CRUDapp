package com.example.kanban.service;

import com.example.kanban.DTO.FeatureDTO;
import com.example.kanban.entity.BoardEntity;
import com.example.kanban.entity.FeatureEntity;
import com.example.kanban.enums.FeatureCategory;
import com.example.kanban.enums.Status;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {
    private final FeatureRepository featureRepository;
    private final BoardRepository boardRepository;
    private final BasicJsonParser parser = new BasicJsonParser();



    @Autowired
    public FeatureService(FeatureRepository featureRepository, BoardRepository boardRepository) {
        this.featureRepository = featureRepository;
        this.boardRepository = boardRepository;
    }

    public List<FeatureDTO> getAllFeatures() {
        return featureRepository.findAll().stream().map(FeatureDTO::from).collect(Collectors.toList());
    }

    public FeatureDTO getFeatureById(Long id) throws NotFoundException {
        Optional<FeatureEntity> featureEntity = featureRepository.findById(id);
        if (featureEntity.isPresent()) {
            return FeatureDTO.from(featureEntity.get());
        } else {
            throw new NotFoundException("Feature not found with id: " + id);
        }
    }

    public FeatureDTO createFeature(FeatureDTO featureDTO) throws NotFoundException {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(featureDTO.getBoard().getId());
        if (boardEntityOptional.isEmpty()) {
            throw new NotFoundException("Board not found with id: " + featureDTO.getBoard().getId());
        }
        BoardEntity boardEntity = boardEntityOptional.get();
        FeatureEntity featureEntity = new FeatureEntity(featureDTO.getTitle(), featureDTO.getDescription(),
                featureDTO.getCategory(), featureDTO.getStatus(), boardEntity);
        boardEntity.addFeature(featureEntity);
        featureRepository.save(featureEntity);

        Optional<FeatureEntity> foundEntity = featureRepository.findById(featureEntity.getId());
        if (foundEntity.isPresent()) {
            return FeatureDTO.from(foundEntity.get());
        } else {
            throw new NotFoundException("Feature was not found");
        }
    }

    public FeatureDTO updateFeature(Long id, String attributes) throws NotFoundException {
        FeatureEntity featureEntity =
                featureRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot update feature that doesn't exist"));
        Map<String, Object> map = parser.parseMap(attributes);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String s = entry.getKey();
            Object o = entry.getValue();
            switch (s) {
                case "title" -> featureEntity.setTitle(o.toString());
                case "description" -> featureEntity.setDescription(o.toString());
                case "category" -> featureEntity.setFeatureCategory(FeatureCategory.valueOf(o.toString()));
                case "status" -> featureEntity.setStatus(Status.valueOf(o.toString()));
            }
        }
        return FeatureDTO.from(featureRepository.save(featureEntity));
    }

    public void deleteFeature(Long id) {
        Optional<FeatureEntity> featureEntity = featureRepository.findById(id);
        featureEntity.ifPresent(featureRepository::delete);
    }

}
