package com.soumyaranjanmohanty.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soumyaranjanmohanty.blog.entites.About;
import com.soumyaranjanmohanty.blog.entites.User;
import com.soumyaranjanmohanty.blog.exceptions.ResourceNotFoundException;
import com.soumyaranjanmohanty.blog.payloads.AboutDto;
import com.soumyaranjanmohanty.blog.repositories.AboutRepo;
import com.soumyaranjanmohanty.blog.repositories.UserRepo;
import com.soumyaranjanmohanty.blog.services.AboutService;

@Service
public class AboutServiceImpl implements AboutService {

	@Autowired
	private AboutRepo aboutrepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public AboutDto createAbout(AboutDto aboutDto,Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		
		About about = this.modelMapper.map(aboutDto,About.class);
		about.setImageName("default.png");
		about.setPostDate(new Date());
		about.setUser(user);
		
		About newAbout = this.aboutrepo.save(about);
		
		return this.modelMapper.map(newAbout, AboutDto.class);
	}

	@Override
	public AboutDto getAboutByID(Integer aboutID) {
		About about = this.aboutrepo.findById(aboutID).orElseThrow(()-> new ResourceNotFoundException("About", "about id", aboutID));
		
		return this.modelMapper.map(about, AboutDto.class);
	}

	@Override
	public AboutDto updateAbout(AboutDto aboutDto, Integer aboutID) {
		
	About about = this.aboutrepo.findById(aboutID).orElseThrow(()-> new ResourceNotFoundException("About ", "about id", aboutID));
	
	about.setContent(aboutDto.getContent());
	about.setImageName(aboutDto.getImageName());
	About updatedAbout = this.aboutrepo.save(about);
		return modelMapper.map(updatedAbout, AboutDto.class);
	}

	@Override
	public void deleteAbout(Integer aboutID) {
		
	About about = this.aboutrepo.findById(aboutID).orElseThrow(()-> new ResourceNotFoundException("About ", "about id", aboutID));
    
	this.aboutrepo.delete(about);
	}

	@Override
	public List<AboutDto> getAboutByUser(Integer userID) {
		
		User user = this.userRepo.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User ","userId", userID));
		List<About> abouts = this.aboutrepo.getAllByUser(user);
		
		List<AboutDto> aboutDtos = abouts.stream().map((about)->this.modelMapper.map(about, AboutDto.class)).collect(Collectors.toList());
		return aboutDtos;
	}

}
