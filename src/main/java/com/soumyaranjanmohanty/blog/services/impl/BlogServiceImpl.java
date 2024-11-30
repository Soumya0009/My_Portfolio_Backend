package com.soumyaranjanmohanty.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumyaranjanmohanty.blog.entites.Blog;
import com.soumyaranjanmohanty.blog.entites.User;
import com.soumyaranjanmohanty.blog.exceptions.ResourceNotFoundException;
import com.soumyaranjanmohanty.blog.payloads.BlogDto;
import com.soumyaranjanmohanty.blog.repositories.BlogRepo;
import com.soumyaranjanmohanty.blog.repositories.UserRepo;
import com.soumyaranjanmohanty.blog.services.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepo blogRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Override
	public BlogDto createBlog(BlogDto blogDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		Blog blog = this.modelMapper.map(blogDto, Blog.class);
		blog.setImageName("default.png");
		blog.setBlogDate(new Date());
		blog.setUser(user);

		Blog newBlog = this.blogRepo.save(blog);

		return this.modelMapper.map(newBlog, BlogDto.class);
	}

	@Override
	public BlogDto updateBlog(BlogDto blogDto, Integer blogId) {
		Blog blog = this.blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog ", "blog id", blogId));
		blog.setTitle(blogDto.getTitle());
		blog.setContent(blogDto.getContent());
		blog.setImageName(blogDto.getImageName());
		Blog updatedBlog = this.blogRepo.save(blog);
		return modelMapper.map(updatedBlog, BlogDto.class);
	}

	@Override
	public void deleteBlog(Integer blogId) {

		Blog blog = this.blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog ", "blog id", blogId));

		this.blogRepo.delete(blog);
	}

	@Override
	public List<BlogDto> getAllBlogs() {
		
		 List<Blog> allBlogs = this.blogRepo.findAll();
		List<BlogDto> blogDtos = allBlogs.stream().map((blog)-> this.modelMapper.map(blog, BlogDto.class)).collect(Collectors.toList());
		
		return blogDtos;
	}

	@Override
	public BlogDto getBlogsId(Integer blogId) {
		Blog blog = this.blogRepo.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog", "blog id", blogId));

		return this.modelMapper.map(blog, BlogDto.class);
	}

	@Override
	public List<BlogDto> getBlogsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","userId", userId));
		List<Blog> blogs = this.blogRepo.getAllByUser(user);
		
		List<BlogDto> blogDtos = blogs.stream().map((blog)->this.modelMapper.map(blog, BlogDto.class)).collect(Collectors.toList());
		return blogDtos;
	}

}
