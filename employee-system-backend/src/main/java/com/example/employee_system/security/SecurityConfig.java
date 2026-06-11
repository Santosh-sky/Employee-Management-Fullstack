package com.example.employee_system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Allow React browser preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public auth APIs
                        .requestMatchers("/auth/**").permitAll()

                        // Department Api
                        .requestMatchers(HttpMethod.GET, "/departments/**")
                        .hasAnyRole("USER", "ADMIN")

                        // USER and ADMIN can view employees
                        .requestMatchers(HttpMethod.GET, "/employees/**")
                        .hasAnyRole("USER", "ADMIN")

                        // Only ADMIN can create employee
                        .requestMatchers(HttpMethod.POST, "/employees")
                        .hasRole("ADMIN")

                        // Only ADMIN can update employee
                        .requestMatchers(HttpMethod.PUT, "/employees/**")
                        .hasRole("ADMIN")

                        // Only ADMIN can delete employee
                        .requestMatchers(HttpMethod.DELETE, "/employees/**")
                        .hasRole("ADMIN")

                        // Any other request needs login
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}