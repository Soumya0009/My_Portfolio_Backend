package com.soumyaranjanmohanty.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumyaranjanmohanty.blog.entites.Project;
import com.soumyaranjanmohanty.blog.entites.User;
import com.soumyaranjanmohanty.blog.exceptions.ResourceNotFoundException;
import com.soumyaranjanmohanty.blog.payloads.ProjectDto;
import com.soumyaranjanmohanty.blog.repositories.ProjectRepo;
import com.soumyaranjanmohanty.blog.repositories.UserRepo;
import com.soumyaranjanmohanty.blog.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ProjectDto createProject(ProjectDto projectDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Project project = this.modelMapper.map(projectDto, Project.class);
        project.setImageName("default.png");
        project.setProjectDate(new Date());
        project.setUser(user);

        Project newProject = this.projectRepo.save(project);

        return this.modelMapper.map(newProject, ProjectDto.class);
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectDto, Integer projectId) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "project id", projectId));
        project.setTitle(projectDto.getTitle());
        project.setContent(projectDto.getContent());
        project.setImageName(projectDto.getImageName());
        Project updatedProject = this.projectRepo.save(project);
        return modelMapper.map(updatedProject, ProjectDto.class);
    }

    @Override
    public void deleteProject(Integer projectId) {

        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "project id", projectId));

        this.projectRepo.delete(project);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        
         List<Project> allProjects = this.projectRepo.findAll();
        List<ProjectDto> projectDtos = allProjects.stream().map((project)-> this.modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        
        return projectDtos;
    }

    @Override
    public ProjectDto getProjectsId(Integer projectId) {
        Project project = this.projectRepo.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "project id", projectId));
        
        return this.modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public List<ProjectDto> getProjectsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","userId", userId));
        List<Project> projects = this.projectRepo.getAllByUser(user);
        
        List<ProjectDto> projectDtos = projects.stream().map((project)->this.modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return projectDtos;
    }

}
