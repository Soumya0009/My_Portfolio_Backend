package com.soumyaranjanmohanty.blog.services;

import java.util.List;

import com.soumyaranjanmohanty.blog.payloads.ProjectDto;

public interface ProjectService {
	
//	Create
	
	ProjectDto createProject (ProjectDto projectDto, Integer userId);
	
//	Update 
	
	ProjectDto updateProject (ProjectDto projectDto, Integer projectId);
	
//	Delete 
	
	void deleteProject(Integer projectId);
	
//	Get all Projects
	
	List<ProjectDto> getAllProjects();
	
//	Get all Projects by Id
	
	ProjectDto getProjectsId(Integer projectId);
	
//	Get all Projects by User Id
	
	List<ProjectDto> getProjectsByUser(Integer userId);
	
	
}
