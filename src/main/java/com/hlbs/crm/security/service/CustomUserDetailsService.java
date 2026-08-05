package com.hlbs.crm.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hlbs.crm.entities.Users;
import com.hlbs.crm.repositories.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByEmailAndIsActive(email, true);
		UserDetails userDetails = null;
		if (user.isPresent()) {
			userDetails = User.withUsername(user.get().getEmail()).password(user.get().getPassword()).roles(user.get().getUserRole().toString()).build();
		}
		else
			throw new UsernameNotFoundException("Login failed for " + email);
		return userDetails;
	}
}
