package com.soumyaranjanmohanty.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SoumyaBackendApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    // Constructor injection for PasswordEncoder
    public SoumyaBackendApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SoumyaBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        // Use the injected passwordEncoder
        System.out.println(this.passwordEncoder.encode("xyz"));
    }
}
