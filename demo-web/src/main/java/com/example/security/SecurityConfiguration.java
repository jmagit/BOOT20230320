package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean 
    public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
    }

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.requestMatchers("/actores/**").hasRole("ADMIN")
//				.requestMatchers("/ajax/**").authenticated()
				.anyRequest().permitAll()
			.and()
	            .formLogin()
	            .loginPage("/mylogin")
	            .permitAll()
	        .and()
	        	.logout()
	        	.logoutSuccessUrl("/")
	        	.permitAll();

		return http.build();
	}
}