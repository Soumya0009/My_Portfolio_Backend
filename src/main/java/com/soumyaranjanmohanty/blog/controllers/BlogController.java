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

import com.soumyaranjanmohanty.blog.payloads.ApiResponse;
import com.soumyaranjanmohanty.blog.payloads.BlogDto;
import com.soumyaranjanmohanty.blog.services.BlogService;
import com.soumyaranjanmohanty.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	Create Blog
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user/{userId}/blog")
	public ResponseEntity<BlogDto> createBlog(
		@RequestBody BlogDto blogDto,
		@PathVariable Integer userId
		){
		BlogDto createBlog = this.blogService.createBlog(blogDto, userId);
		return new ResponseEntity<BlogDto>(createBlog,HttpStatus.CREATED);
	}
	
//	Delete Blog
	@PreAuthorize("hasRole('ADMIN')") 
	@DeleteMapping("/blog/{blogId}")
	public ApiResponse deleteBlog(
			@PathVariable Integer blogId) {
		this.blogService.deleteBlog(blogId);
		return new ApiResponse("Post is successfully deleted !!!", true);
	}
	
//	Update/Put Blog
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/blog/{blogId}")
	public ResponseEntity<BlogDto> updateAbout(
	        @RequestBody BlogDto blogDto,
	        @PathVariable Integer blogId) {
	    BlogDto updatedBlog = this.blogService.updateBlog(blogDto, blogId);
	    return new ResponseEntity<BlogDto>(updatedBlog, HttpStatus.OK);
	}
	
// Get Blog by id
	
	@GetMapping("/blog/{blogId}")
	public ResponseEntity<BlogDto> getAllBlogById(@PathVariable Integer blogId){
		
		BlogDto blogDto = this.blogService.getBlogsId(blogId);
		
		return new ResponseEntity<BlogDto>(blogDto,HttpStatus.OK);
	}
	
//	Get Blog by user
	
	@GetMapping("/user/{userId}/blog")
	public ResponseEntity<List<BlogDto>> getBlogByUser(
			@PathVariable Integer userId
			){
		List<BlogDto> blogs = this.blogService.getBlogsByUser(userId);
		
		return new ResponseEntity<List<BlogDto>>(blogs,HttpStatus.OK);
	}
	
//	Get All Blogs
	@GetMapping("/blogs")
	public ResponseEntity<List<BlogDto>> getAllBlogs(){
	List<BlogDto> allBlogs = this.blogService.getAllBlogs();
	return new ResponseEntity<List<BlogDto>>(allBlogs,HttpStatus.OK);
	}
	
	// Blog Image Upload
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/blog/image/upload/{blogId}")
		public ResponseEntity<BlogDto> uploadBlogImage(
		        @RequestParam("image") MultipartFile image,
		        @PathVariable Integer blogId) throws IOException {

		    String fileName = this.fileService.uploadImage(path, image);
		    BlogDto blogDto = this.blogService.getBlogsId(blogId);
		    blogDto.setImageName(fileName);
		    BlogDto updateBlog = this.blogService.updateBlog(blogDto, blogId);
		    return new ResponseEntity<BlogDto>(updateBlog, HttpStatus.OK);
		}
		

		// Blog Image Serve
		@GetMapping(value = "/blog/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
		        @PathVariable String imageName,
		        HttpServletResponse response) throws IOException {
		    
		    InputStream resource = this.fileService.getResource(path, imageName);
		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    
		    // Use the correct StreamUtils.copy method
		    StreamUtils.copy(resource, response.getOutputStream());
		}
}
