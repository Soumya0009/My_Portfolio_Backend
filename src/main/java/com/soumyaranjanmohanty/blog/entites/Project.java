package com.soumyaranjanmohanty.blog.entites;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer projectID;
	
	@Column(name = "project_title", length = 1000, nullable = false)
	private String title;
	
	@Column(name = "project_content", length = 5000, nullable = false)
	private String content;
	
	private String ImageName;
	
	private Date projectDate;
	
	@ManyToOne
	private User user;
}
