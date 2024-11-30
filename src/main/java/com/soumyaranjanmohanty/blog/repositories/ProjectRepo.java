package com.soumyaranjanmohanty.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumyaranjanmohanty.blog.entites.Project;
import com.soumyaranjanmohanty.blog.entites.User;

public interface ProjectRepo extends JpaRepository<Project, Integer>{
	List<Project> getAllByUser(User user);
}
