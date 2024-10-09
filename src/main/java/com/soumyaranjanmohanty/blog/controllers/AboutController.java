package com.soumyaranjanmohanty.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumyaranjanmohanty.blog.payloads.AboutDto;
import com.soumyaranjanmohanty.blog.payloads.ApiResponse;
import com.soumyaranjanmohanty.blog.services.AboutService;

@RestController
@RequestMapping("/api")
public class AboutController {
	
	@Autowired
	private AboutService aboutService;
	
//	Create about
	
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
	@DeleteMapping("/about/{aboutId}")
	public ApiResponse deleteAbout(
			@PathVariable Integer aboutId) {
		this.aboutService.deleteAbout(aboutId);
		return new ApiResponse("Post is successfully deleted !!!", true);
	}
	
//	Update about by ID
	@PutMapping("/about/{aboutId}")
	public ResponseEntity<AboutDto> updateAbout(
			@RequestBody AboutDto aboutDto,
			@PathVariable Integer aboutId) {
		AboutDto updatedAbout = this.aboutService.updateAbout(aboutDto, aboutId);
		return new ResponseEntity<AboutDto>(updatedAbout,HttpStatus.OK);
	}
	
}
