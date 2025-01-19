package com.soumyaranjanmohanty.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soumyaranjanmohanty.blog.entites.User;
import com.soumyaranjanmohanty.blog.exceptions.ResourceNotFoundException;
import com.soumyaranjanmohanty.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Loading User from database by username
		
		User user =this.userRepo.findByName(username).orElseThrow(()->new ResourceNotFoundException("User "," Name: "+username, 0));
		
		return user;
	}
	
	

}
