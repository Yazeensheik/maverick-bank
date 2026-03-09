package com.wipro.maverick_bank.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Public frontend pages and static resources
                .requestMatchers(
                        "/",
                        "/index.html",
                        "/login.html",
                        "/dashboard.html",
                        "/register.html",
                        "/about.html",
                        "/contact.html",
                        "/pages/**",
                        "/components/**",
                        "/services/**",
                        "/dashboards/**",
                        "/shared/**",
                        "/modules/**",
                        "/css/**",
                        "/js/**",
                        "/assets/**",
                        "/favicon.ico"
                ).permitAll()


                // 🔓 Swagger

                // Swagger
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                ).permitAll()


                // 🔓 Auth APIs
                .requestMatchers("/auth/**", "/api/auth/**", "/api/users/login","/api/users/add/customer",
                		"/api/users/add/employee").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/loan-applications/**").permitAll()
                
                .requestMatchers("/api/transactions/**").permitAll()
                .requestMatchers("/api/statements/**").permitAll()

                // 🔐 Everything else requires login
                .anyRequest().authenticated()
            )

            // ❌ Disable browser popup login
//            .httpBasic(httpBasic -> httpBasic.disable());
//
//                // Public auth APIs
//                .requestMatchers("/auth/**").permitAll()
//
//                // All other requests require authentication
//                .anyRequest().authenticated()
//            )

            // Basic Auth without browser popup
            .httpBasic(httpBasic ->
                httpBasic.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}