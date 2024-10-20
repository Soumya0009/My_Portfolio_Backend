package com.soumyaranjanmohanty.blog.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BlogDto {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String content;

	@NotEmpty
	private String imageName;

	private Date blogDate;

	private UserDto user;
}
