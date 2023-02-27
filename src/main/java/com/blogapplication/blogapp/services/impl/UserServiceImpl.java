package com.blogapplication.blogapp.services.impl;

import com.blogapplication.blogapp.entity.User;
import com.blogapplication.blogapp.exceptions.ResourceNotFoundException;
import com.blogapplication.blogapp.payloads.UserDto;
import com.blogapplication.blogapp.repository.UserRepo;
import com.blogapplication.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto); //DONE SINCE WE NEEDED User for saving in DB
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        //update user details using getters and setters
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        //save user to db
        User updatedUser = this.userRepo.save(user);

        //convert User entity to UserDto
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();
        List<UserDto> allUsers = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return allUsers;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        this.userRepo.delete(user);

    }

    //to convert UserDto to User
    private User dtoToUser(UserDto userDto){
        //using model mapper- new method
        User user = this.modelMapper.map(userDto,User.class);

//        old method

//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());


        return user;
    }

    //to convert User to UserDto
    private UserDto userToDto(User user){
        //new method
        UserDto userDto = this.modelMapper.map(user,UserDto.class);

//        old method

//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
