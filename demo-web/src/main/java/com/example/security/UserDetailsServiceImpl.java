package com.example.security;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		switch(username) {
		case "user": 		return this.userBuilder(username, passwordEncoder.encode("user"), "USER");
		case "manager": 	return this.userBuilder(username, passwordEncoder.encode("manager"), "MANAGER");
		case "admin": 		return this.userBuilder(username, passwordEncoder.encode("admin"), "USER", "MANAGER", "ADMIN");
		default: throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}
	private User userBuilder(String username, String password, String... roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return new User(username, password, /* enabled */ true, /* accountNonExpired */ true,
				/* credentialsNonExpired */ true, /* accountNonLocked */ true, authorities);
	}}
