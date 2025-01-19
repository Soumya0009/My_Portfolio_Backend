package com.soumyaranjanmohanty.blog.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 7, message = "User name must be a minimum of 7 characters")
    private String name;

    @NotEmpty
    @Size(min = 5, max = 11)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$", 
             message = "Password must be between 5 and 11 characters, containing at least 1 uppercase, 1 lowercase, 1 digit, and 1 special character")
    private String password;

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }
    
    @JsonProperty
    public void setPassword(String password) {
    	this.password = password;
    }
}
