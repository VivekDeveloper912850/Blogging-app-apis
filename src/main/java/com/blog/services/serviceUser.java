package com.blog.services;

import com.blog.payloads.UserDto;

import java.util.List;

public interface serviceUser {
    UserDto registerNewUser(UserDto user);
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user ,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
