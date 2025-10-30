package com.vijayasree.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig: simple RBAC via in-memory users for now.
 * Replace with JWT (auth-service) later.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(reg -> reg
				.requestMatchers("/actuator/health", "/h2-console/**").permitAll()
				.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.headers(h -> h.frameOptions(f -> f.disable())) // allow H2 console
			.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		return new InMemoryUserDetailsManager(
			User.withUsername("admin").password(encoder.encode("admin123")).roles("ADMIN").build(),
			User.withUsername("manager").password(encoder.encode("manager123")).roles("MANAGER").build(),
			User.withUsername("accountant").password(encoder.encode("account123")).roles("ACCOUNTANT").build(),
			User.withUsername("cashier").password(encoder.encode("cashier123")).roles("CASHIER").build()
		);
	}

	@Bean
	public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
