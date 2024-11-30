package com.soumyaranjanmohanty.blog.services;

import java.util.List;

import com.soumyaranjanmohanty.blog.payloads.AboutDto;

public interface AboutService {
	
//	Create
	
	AboutDto createAbout(AboutDto aboutDto, Integer userId);
	
//	Get/Read
	
	AboutDto getAboutByID(Integer aboutID); 
	
//	Update
	
	AboutDto updateAbout(AboutDto aboutDto, Integer aboutID);
	
//	Delete
	
	void deleteAbout(Integer aboutID);
	
//	Get all post by user
	
	List<AboutDto> getAboutByUser(Integer userID);
	

}
