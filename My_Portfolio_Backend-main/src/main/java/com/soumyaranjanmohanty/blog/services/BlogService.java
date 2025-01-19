package com.soumyaranjanmohanty.blog.services;

import java.util.List;

import com.soumyaranjanmohanty.blog.payloads.BlogDto;

public interface BlogService {
	
//	Create
	
	BlogDto createBlog (BlogDto blogDto, Integer userId);
	
//	Update 
	
	BlogDto updateBlog (BlogDto blogDto, Integer blogId);
	
//	Delete 
	
	void deleteBlog(Integer blogId);
	
//	Get all blogs
	
	List<BlogDto> getAllBlogs();
	
//	Get all blogs by Id
	
	BlogDto getBlogsId(Integer blogId);
	
//	Get all blogs by User Id
	
	List<BlogDto> getBlogsByUser(Integer userId);
	
	
}
