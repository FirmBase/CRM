package com.hlbs.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hlbs.crm.dtos.UsersDTO;
import com.hlbs.crm.entities.Users;
import com.hlbs.crm.enumerations.UserRoleEnum;
import com.hlbs.crm.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;

	public Long getIdByEmail(final String email) {
		return usersRepository.findByEmail(email).orElseThrow().getId();
	}

	public List<UsersDTO> getAllUsers() {
		final List<UsersDTO> usersDTOs = new ArrayList<UsersDTO>();
		usersRepository.findAll().forEach(user -> {
			final UsersDTO usersDTO = new UsersDTO();
			BeanUtils.copyProperties(user, usersDTO);
			// usersDTO.setUserRole(user.getUserRoleEnum());
			usersDTOs.add(usersDTO);
		});
		return usersDTOs;
	}

	public List<UsersDTO> getAllUsersByActivity(final boolean isActive) {
		final List<UsersDTO> usersDTOs = new ArrayList<UsersDTO>();
		usersRepository.getAllUsersByActivity(isActive).forEach(user -> {
			final UsersDTO usersDTO = new UsersDTO();
			BeanUtils.copyProperties(user, usersDTO);
			usersDTOs.add(usersDTO);
		});
		return usersDTOs;
	}

	public List<UsersDTO> findAllByIsActive(final boolean isActive) {
		final List<UsersDTO> usersDTOs = new ArrayList<UsersDTO>();
		usersRepository.findAllByIsActive(isActive).forEach(user -> {
			final UsersDTO usersDTO = new UsersDTO();
			BeanUtils.copyProperties(user, usersDTO);
			usersDTOs.add(usersDTO);
		});
		return usersDTOs;
	}

	public void addUser(final UsersDTO usersDTO) {
		final Users user = new Users();
		BeanUtils.copyProperties(usersDTO, user);
		user.setRegisteredAt(new Date());
		user.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
		user.setIsActive(true);
		// user.setUserRoleEnum(usersDTO.getUserRole());
		usersRepository.save(user);
	}

	public long countByEmail(final String email) {
		return usersRepository.countByEmail(email);
	}

	public void toggleUserIsActive(final long id, final boolean isActive) {
		usersRepository.toggleUserIsActive(id, isActive);
	}

	public void changeUserRole(final long id, final UserRoleEnum userRole) {
		usersRepository.changeUserRole(id, userRole);
	}

	public void removeUser(final long id) {
		usersRepository.deleteById(id);
	}
}
