package com.example.kanban.controller;

import com.example.kanban.DTO.UserDto;
import com.example.kanban.entity.UserEntity;
import com.example.kanban.exception.InvalidUserException;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List <UserDto> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) throws NotFoundException {
        return service.getUserById(id);
    }

    @PostMapping("/")
    public UserDto create(@RequestBody UserEntity userEntity) throws InvalidUserException {
        return service.createUser(userEntity);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody String attributes) throws NotFoundException {
        return service.updateUser(id, attributes);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteUser(id);
    }

}

