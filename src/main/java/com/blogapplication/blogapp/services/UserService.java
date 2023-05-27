package com.blogapplication.blogapp.services;

import com.blogapplication.blogapp.payloads.UserDto;

import java.util.List;

public interface UserService {
//    UserDto registerNewUser(UserDto userDto);
    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

    UserDto registerNewUser(UserDto userDto);
}
