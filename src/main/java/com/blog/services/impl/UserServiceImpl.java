package com.blog.services.impl;

import com.blog.config.AppConstants;
import com.blog.entities.Category;
import com.blog.entities.Roles;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.payloads.categoryDto;
import com.blog.repository.RoleRepo;
import com.blog.repository.UserRepo;
import com.blog.services.serviceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl  implements serviceUser {
    @Autowired
    private UserRepo repo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // roles
        Roles role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRole().add(role);

        User newUser = this.repo.save(user);

        return this.modelMapper.map(newUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
      User savedUser =   this.repo.save(user);
        return this.userTodto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "Id" , userId));
         user.setName(userDto.getName());
         user.setEmail(userDto.getEmail());
         user.setAbout(userDto.getAbout());
         user.setPassword(userDto.getPassword());
         User updateUser = this.repo.save(user);
      UserDto userDto1 =    this.userTodto(updateUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User update = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "userId" , userId));
        return this.userTodto(update);
    }


    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.repo.findAll();
       List<UserDto> userDtos =  users.stream().map(user->this.userTodto(user)).collect(Collectors.toList());
        return userDtos;
    }
    @Override
    public void deleteUser(Integer userId) {
        User user = this.repo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "Id" , userId));
        this.repo.delete(user);
    }

    private User dtoToUser(UserDto userDto){

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto userTodto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAbout(user.getAbout());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
