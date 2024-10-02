package com.soumyaranjanmohanty.blog.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin") // Defines the table name in the database
@NoArgsConstructor
@Getter
@Setter
public class User {
	// Using IDENTITY to auto-generate primary key values
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 25)
    private String password;
}