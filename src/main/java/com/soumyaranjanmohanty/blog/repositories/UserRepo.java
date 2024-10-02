package com.soumyaranjanmohanty.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumyaranjanmohanty.blog.entites.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
