package com.soumyaranjanmohanty.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumyaranjanmohanty.blog.entites.About;
import com.soumyaranjanmohanty.blog.entites.User;

public interface AboutRepo extends JpaRepository<About, Integer>{

	List<About> getAllByUser(User user);
}
