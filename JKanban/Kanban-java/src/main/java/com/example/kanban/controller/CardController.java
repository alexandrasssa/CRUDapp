package com.example.kanban.controller;

import com.example.kanban.DTO.CardDTO;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CardDTO getById(@PathVariable Long id) throws NotFoundException {
        return service.getCardById(id);
    }

    @GetMapping()
    public List<CardDTO> getAll() throws NotFoundException {
        return service.getAllCards();
    }

    @GetMapping("/feature/{featureId}")
    public List<CardDTO> getByFeatureId(@PathVariable Long featureId) throws NotFoundException {
        return service.getCardsByFeatureId(featureId);
    }

    @PostMapping("/")
    public CardDTO create(@RequestBody CardDTO card) throws NotFoundException {
        return service.createCard(card);
    }

    @PutMapping("/{id}")
    public CardDTO update(@PathVariable Long id, @RequestBody String attributes) throws NotFoundException {
        return service.updateCard(id, attributes);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCard(id);
    }
}
