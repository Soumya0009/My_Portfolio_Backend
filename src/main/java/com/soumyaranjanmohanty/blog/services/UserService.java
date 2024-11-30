package com.soumyaranjanmohanty.blog.services;

import org.springframework.stereotype.Service;

import com.soumyaranjanmohanty.blog.payloads.UserDto;

public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    
    
}
