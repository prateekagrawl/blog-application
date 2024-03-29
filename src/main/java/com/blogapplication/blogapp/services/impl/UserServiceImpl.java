package com.blogapplication.blogapp.services.impl;

import com.blogapplication.blogapp.config.AppConstants;
import com.blogapplication.blogapp.entity.Role;
import com.blogapplication.blogapp.entity.User;
import com.blogapplication.blogapp.exceptions.ResourceNotFoundException;
import com.blogapplication.blogapp.payloads.UserDto;
import com.blogapplication.blogapp.repository.RoleRepo;
import com.blogapplication.blogapp.repository.UserRepo;
import com.blogapplication.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);

        //encode the password
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        //by default a normal user Role to be added
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User savedUser = this.userRepo.save(user);
        return this.modelMapper.map(savedUser,UserDto.class);
    }

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
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }

    //to convert User to UserDto
    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
