package com.example.kanban.controller;

import com.example.kanban.DTO.FeatureDTO;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature")
public class FeatureController {

    private final FeatureService service;

    @Autowired
    public FeatureController(FeatureService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public FeatureDTO getById(@PathVariable Long id) throws NotFoundException {
        return service.getFeatureById(id);
    }

    @GetMapping
    public List<FeatureDTO> getAll() throws NotFoundException {
        return service.getAllFeatures();
    }

    @PostMapping("/")
    public FeatureDTO create(@RequestBody FeatureDTO card) throws NotFoundException {
        return service.createFeature(card);
    }

    @PutMapping("/{id}")
    public FeatureDTO update(@PathVariable Long id, @RequestBody String attributes) throws NotFoundException {
        return service.updateFeature(id, attributes);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteFeature(id);
    }
}
