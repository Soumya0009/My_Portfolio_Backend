package com.soumyaranjanmohanty.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumyaranjanmohanty.blog.exceptions.UnauthorizedException;
import com.soumyaranjanmohanty.blog.payloads.JwtAuthRequest;
import com.soumyaranjanmohanty.blog.payloads.JwtAuthResponse;
import com.soumyaranjanmohanty.blog.payloads.UserDto;
import com.soumyaranjanmohanty.blog.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "${app.frontend-url}")  // Inject frontend URL from application.properties
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper mapper; // Add ModelMapper

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
        // Authenticate user credentials
        this.authenticate(request.getUsername(), request.getPassword());

        // Load user details
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT token
        String token = this.jwtTokenHelper.generateToken(userDetails);

        // Map UserDetails to UserDto
        UserDto userDto = new UserDto();
        userDto.setName(userDetails.getUsername());
        // Populate other fields manually or using a custom UserDetails implementation

        // Prepare and return response
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser(userDto);

        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new UnauthorizedException("User account is disabled");
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}















//package com.soumyaranjanmohanty.blog.controllers;

//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.soumyaranjanmohanty.blog.exceptions.UnauthorizedException;
//import com.soumyaranjanmohanty.blog.payloads.JwtAuthRequest;
//import com.soumyaranjanmohanty.blog.payloads.JwtAuthResponse;
//import com.soumyaranjanmohanty.blog.payloads.UserDto;
//import com.soumyaranjanmohanty.blog.security.JwtTokenHelper;
//
//@RestController
//@RequestMapping("/api/v1/auth/")
//@CrossOrigin(origins = "http://localhost:5173")
//public class AuthController {
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private ModelMapper mapper; // Add ModelMapper
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
//        // Authenticate user credentials
//        this.authenticate(request.getUsername(), request.getPassword());
//
//        // Load user details
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//
//        // Generate JWT token
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        // Map UserDetails to UserDto
//        UserDto userDto = new UserDto();
//        userDto.setName(userDetails.getUsername());
//        // Populate other fields manually or using a custom UserDetails implementation
//
//        // Prepare and return response
//        JwtAuthResponse response = new JwtAuthResponse();
//        response.setToken(token);
//        response.setUser(userDto);
//
//        return ResponseEntity.ok(response);
//    }
//
//    private void authenticate(String username, String password) {
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, password);
//            this.authenticationManager.authenticate(authenticationToken);
//        } catch (DisabledException e) {
//            throw new UnauthorizedException("User account is disabled");
//        } catch (BadCredentialsException e) {
//            throw new UnauthorizedException("Invalid username or password");
//        }
//    }
//}