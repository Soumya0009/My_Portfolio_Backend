package com.soumyaranjanmohanty.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soumyaranjanmohanty.blog.entites.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByName(String name);
}
