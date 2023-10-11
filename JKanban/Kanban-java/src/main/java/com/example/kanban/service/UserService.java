package com.example.kanban.service;

import com.example.kanban.DTO.UserDto;
import com.example.kanban.entity.UserEntity;
import com.example.kanban.exception.InvalidUserException;
import com.example.kanban.exception.NotFoundException;
import com.example.kanban.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final BasicJsonParser parser = new BasicJsonParser();
    private final ValidatorFactory validatorFactory;

    @Autowired
    public UserService(UserRepository repository, ValidatorFactory validatorFactory) {
        this.repository = repository;
        this.validatorFactory = validatorFactory;
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = repository.findAll();
        return userEntities.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) throws NotFoundException {
        Optional<UserEntity> user = repository.findById(id);
        if (user.isPresent()) {
            return UserDto.from(user.get());
        } else {
            throw new NotFoundException("User not found with id: " + id);
        }
    }

    public UserDto createUser(UserEntity userEntity) throws InvalidUserException {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
        if (!violations.isEmpty()) {
            throw new InvalidUserException("Cannot create user with given parameters");
        }

        return UserDto.from(repository.save(userEntity));
    }

    public UserDto updateUser(Long id, String attributes) throws NotFoundException {
        Optional<UserEntity> userEntity = repository.findById(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Cannot update user that doesn't exist");
        }
        userEntity.ifPresent(user -> {
                    Map<String, Object> map = parser.parseMap(attributes);
                    map.forEach((s, o) -> {
                        switch (s) {
                            case "username" -> user.setUsername(o.toString());
                        }
                    });
                }
        );

        return UserDto.from(repository.save(userEntity.get()));
    }

    public void deleteUser(Long id) {
        Optional<UserEntity> user = repository.findById(id);
        user.ifPresent(u -> repository.delete(u));
    }

}
