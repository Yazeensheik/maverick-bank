package com.wipro.maverick_bank.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated())
				.httpBasic();

		return http.build();
	}

	/**
	 * 🔥 MOST IMPORTANT PART Forces Spring Security to use DB-based authentication
	 * with PLAIN TEXT passwords
	 */
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);

		// ✅ CORRECT way (NO bcrypt, plain text)
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}