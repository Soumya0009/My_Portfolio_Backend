package com.soumyaranjanmohanty.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumyaranjanmohanty.blog.entites.Blog;
import com.soumyaranjanmohanty.blog.entites.User;

public interface BlogRepo extends JpaRepository<Blog, Integer>{
	List<Blog> getAllByUser(User user);
}
