package com.soumyaranjanmohanty.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.soumyaranjanmohanty.blog.payloads.AboutDto;
import com.soumyaranjanmohanty.blog.payloads.ApiResponse;
import com.soumyaranjanmohanty.blog.services.AboutService;
import com.soumyaranjanmohanty.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AboutController {
	
	@Autowired
	private AboutService aboutService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	Create about
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user/{userId}/about")
	public ResponseEntity<AboutDto> createAbout(
			@RequestBody AboutDto aboutDto,
			@PathVariable Integer userId){
		AboutDto createAbout = this.aboutService.createAbout(aboutDto, userId);
		return new ResponseEntity<AboutDto>(createAbout,HttpStatus.CREATED);
		
	}
	
//	Get about by users
	
	@GetMapping("/user/{userId}/about")
	public ResponseEntity<List<AboutDto>> getAboutByUser(
			@PathVariable Integer userId
			){
		List<AboutDto> abouts = this.aboutService.getAboutByUser(userId);
		
		return new ResponseEntity<List<AboutDto>>(abouts,HttpStatus.OK);
	}

//	Get about by id
	@GetMapping("/about/{aboutId}")
	public ResponseEntity<AboutDto> getAllAboutById(@PathVariable Integer aboutId){
		
		AboutDto aboutDto = this.aboutService.getAboutByID(aboutId);
		
		return new ResponseEntity<AboutDto>(aboutDto,HttpStatus.OK);
	}
	
//	Delete about by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/about/{aboutId}")
	public ApiResponse deleteAbout(
			@PathVariable Integer aboutId) {
		this.aboutService.deleteAbout(aboutId);
		return new ApiResponse("Post is successfully deleted !!!", true);
	}
	
//	Update about by ID
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/about/{aboutId}")
	public ResponseEntity<AboutDto> updateAbout(
			@RequestBody AboutDto aboutDto,
			@PathVariable Integer aboutId) {
		AboutDto updatedAbout = this.aboutService.updateAbout(aboutDto, aboutId);
		return new ResponseEntity<AboutDto>(updatedAbout,HttpStatus.OK);
	}
	
	// About Image Upload
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/about/image/upload/{aboutId}")
	public ResponseEntity<AboutDto> uploadAboutImage(
	        @RequestParam MultipartFile image,
	        @PathVariable Integer aboutId) throws IOException {

	    String fileName = this.fileService.uploadImage(path, image);
	    AboutDto aboutDto = this.aboutService.getAboutByID(aboutId);
	    aboutDto.setImageName(fileName);
	    AboutDto updateAbout = this.aboutService.updateAbout(aboutDto, aboutId);
	    return new ResponseEntity<AboutDto>(updateAbout, HttpStatus.OK);
	}
	
	// About Image Serve
	@GetMapping(value = "/about/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
	        @PathVariable String imageName,
	        HttpServletResponse response) throws IOException {
	    
	    InputStream resource = this.fileService.getResource(path, imageName);
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    
	    // Use the correct StreamUtils.copy method
	    StreamUtils.copy(resource, response.getOutputStream());
	}

}
