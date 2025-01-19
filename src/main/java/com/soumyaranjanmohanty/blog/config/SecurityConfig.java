package com.soumyaranjanmohanty.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.soumyaranjanmohanty.blog.security.CustomUserDetailService;
import com.soumyaranjanmohanty.blog.security.JWTAuthenticationEntryPiont;
import com.soumyaranjanmohanty.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    private final JWTAuthenticationEntryPiont jwtAuthenticationEntryPiont;

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailService customUserDetailService,
                          JWTAuthenticationEntryPiont jwtAuthenticationEntryPiont,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailService = customUserDetailService;
        this.jwtAuthenticationEntryPiont = jwtAuthenticationEntryPiont;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // CORS Filter
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow all origins, headers, and methods
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Enables CORS filter
            .and()
            .csrf().disable() // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll() // Permit all for auth endpoints
                .requestMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated() // Require authentication for other endpoints
            )
            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPiont) // Handle unauthorized requests
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless authentication (no session management)

        return http.build();
    }

    // Authentication Provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}







//package com.soumyaranjanmohanty.blog.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.web.filter.CorsFilter;
//
//import com.soumyaranjanmohanty.blog.security.CustomUserDetailService;
//import com.soumyaranjanmohanty.blog.security.JWTAuthenticationEntryPiont;
//import com.soumyaranjanmohanty.blog.security.JwtAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Autowired
//    private final CustomUserDetailService customUserDetailService;
//
//    @Autowired
//    private final JWTAuthenticationEntryPiont jwtAuthenticationEntryPiont;
//
//    @Autowired
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    public SecurityConfig(CustomUserDetailService customUserDetailService,
//                          JWTAuthenticationEntryPiont jwtAuthenticationEntryPiont,
//                          JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.customUserDetailService = customUserDetailService;
//        this.jwtAuthenticationEntryPiont = jwtAuthenticationEntryPiont;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////            .cors().and()
////            .authorizeHttpRequests(auth -> auth
////                .requestMatchers("/api/v1/auth/login").permitAll()
////                .requestMatchers(HttpMethod.GET).permitAll()
////                .anyRequest().authenticated()
////            )
////            .exceptionHandling()
////                .authenticationEntryPoint(jwtAuthenticationEntryPiont)
////            .and()
////            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
////            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
////        return http.build();
////    }
//
//    
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Allow all origins, headers, and methods
//        config.setAllowCredentials(true);
//        config.addAllowedOriginPattern("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .cors() // Enables the CORS filter
//            .and()
//            .csrf().disable() // Disable CSRF for stateless APIs
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/v1/auth/**").permitAll() // Permit all for auth endpoints
//                .anyRequest().authenticated()
//            );
//
//        return http.build();
//    }
//
//    
//    
//    
//    
////    @Bean
////    public FilterRegistrationBean<CorsFilter> coresFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.setAllowCredentials(true);
////        corsConfiguration.addAllowedOriginPattern("*"); // Replace "*" with allowed origins for production
////        corsConfiguration.addAllowedHeader("Authorization");
////        corsConfiguration.addAllowedHeader("Content-Type");
////        corsConfiguration.addAllowedHeader("Accept");
////        corsConfiguration.addAllowedMethod("POST");
////        corsConfiguration.addAllowedMethod("GET");
////        corsConfiguration.addAllowedMethod("DELETE");
////        corsConfiguration.addAllowedMethod("PUT");
////        corsConfiguration.addAllowedMethod("OPTIONS");
////
////        source.registerCorsConfiguration("/**", corsConfiguration);
////
////        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
////        bean.setOrder(-110); // Set the order of this filter
////        return bean;
////    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(customUserDetailService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
