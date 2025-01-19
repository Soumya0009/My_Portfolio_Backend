package com.soumyaranjanmohanty.blog.entites;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="blogs")
@Getter
@Setter
@NoArgsConstructor
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer blogID;
	
	@Column(name = "blog_title", length = 1500, nullable = false)
	private String title;
	
	@Lob
	@Column(name = "blog_content", columnDefinition = "LONGTEXT", nullable = false)
	private String content;
	
	private String ImageName;
	
	private Date blogDate;
	
	@ManyToOne
	private User user;
}
