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
@Table(name="about")
@Getter
@Setter
@NoArgsConstructor
public class About {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer aboutID;
	
	@Lob
	@Column(name = "about_content", columnDefinition = "LONGTEXT", nullable = false)
	private String content;
	
	private String ImageName;
	
	private Date postDate;
	
	@ManyToOne
	private User user;
}
