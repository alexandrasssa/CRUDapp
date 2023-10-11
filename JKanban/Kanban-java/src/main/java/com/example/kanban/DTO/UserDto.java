package com.example.kanban.DTO;

import com.example.kanban.entity.UserEntity;

public class UserDto {
    private int id;
    private String username;


    public UserDto(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    static public UserDto from(UserEntity userEntity) {
        return new UserDto(userEntity.getUsername(), userEntity.getId().intValue());
    }
}
