package com.soumyaranjanmohanty.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soumyaranjanmohanty.blog.payloads.ApiResponse;
import com.soumyaranjanmohanty.blog.payloads.ProjectDto;
import com.soumyaranjanmohanty.blog.services.FileService;
import com.soumyaranjanmohanty.blog.services.ProjectService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
    
    // Create Project
	@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/{userId}/project")
    public ResponseEntity<ProjectDto> createProject(
        @RequestBody ProjectDto projectDto,
        @PathVariable Integer userId
        ){
        ProjectDto createProject = this.projectService.createProject(projectDto, userId);
        return new ResponseEntity<ProjectDto>(createProject,HttpStatus.CREATED);
    }
    
    // Delete Project
	@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/project/{projectId}")
    public ApiResponse deleteProject(
            @PathVariable Integer projectId) {
        this.projectService.deleteProject(projectId);
        return new ApiResponse("Project is successfully deleted !!!", true);
    }
    
    // Update/Put Project
	@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/project/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(
            @RequestBody ProjectDto projectDto,
            @PathVariable Integer projectId) {
        ProjectDto updatedProject = this.projectService.updateProject(projectDto, projectId);
        return new ResponseEntity<ProjectDto>(updatedProject, HttpStatus.OK);
    }
    
    // Get Project by id
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Integer projectId){
        ProjectDto projectDto = this.projectService.getProjectsId(projectId);
        return new ResponseEntity<ProjectDto>(projectDto,HttpStatus.OK);
    }
    
    // Get Project by user
    @GetMapping("/user/{userId}/project")
    public ResponseEntity<List<ProjectDto>> getProjectsByUser(
            @PathVariable Integer userId
            ){
        List<ProjectDto> projects = this.projectService.getProjectsByUser(userId);
        return new ResponseEntity<List<ProjectDto>>(projects,HttpStatus.OK);
    }
    
    // Get All Projects
    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getAllProjects(){
        List<ProjectDto> allProjects = this.projectService.getAllProjects();
        return new ResponseEntity<List<ProjectDto>>(allProjects,HttpStatus.OK);
    }
    
 // Project Image Upload
        @PreAuthorize("hasRole('ADMIN')")
 		@PostMapping("/project/image/upload/{projectId}")
 		public ResponseEntity<ProjectDto> uploadProjectImage(
 		        @RequestParam MultipartFile image,
 		        @PathVariable Integer projectId) throws IOException {

 		    String fileName = this.fileService.uploadImage(path, image);
 		    ProjectDto projectDto = this.projectService.getProjectsId(projectId);
 		    projectDto.setImageName(fileName);
 		    ProjectDto updateProject = this.projectService.updateProject(projectDto, projectId);
 		    return new ResponseEntity<ProjectDto>(updateProject, HttpStatus.OK);
 		}
 		

 		// Project Image Serve
 		@GetMapping(value = "/project/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
 		public void downloadImage(
 		        @PathVariable String imageName,
 		        HttpServletResponse response) throws IOException {
 		    
 		    InputStream resource = this.fileService.getResource(path, imageName);
 		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
 		    
 		    // Use the correct StreamUtils.copy method
 		    StreamUtils.copy(resource, response.getOutputStream());
 		}
}
