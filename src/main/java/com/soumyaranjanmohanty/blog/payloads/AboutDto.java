package com.soumyaranjanmohanty.blog.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AboutDto {
	
	@NotEmpty
	private String content;
	
	@NotEmpty
	private String imageName;

	private Date postDate;

	private UserDto user;

}
