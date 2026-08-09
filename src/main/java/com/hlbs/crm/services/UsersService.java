package com.hlbs.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hlbs.crm.dtos.UsersDTO;
import com.hlbs.crm.entities.UserPasswordPasscode;
import com.hlbs.crm.entities.Users;
import com.hlbs.crm.enumerations.UserRoleEnum;
import com.hlbs.crm.repositories.UserPasswordPasscodeRepository;
import com.hlbs.crm.repositories.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UserPasswordPasscodeRepository userPasswordPasscodeRepository;

	@Autowired
	private EmailService emailService;

	public UsersDTO getByEmail(final String email) {
		final UsersDTO usersDTO = new UsersDTO();
		BeanUtils.copyProperties(usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Failed to get session user")), usersDTO);
		return usersDTO;
	}

	public Long getIdByEmail(final String email) {
		return usersRepository.findByEmail(email).orElseThrow().getId();
	}

	public List<UsersDTO> getAllUsers() {
		final List<UsersDTO> usersDTOs = new ArrayList<UsersDTO>();
		usersRepository.findAll().forEach(user -> {
			final UsersDTO usersDTO = new UsersDTO();
			BeanUtils.copyProperties(user, usersDTO);
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
		usersRepository.save(user);
	}

	public long countByEmail(final String email) {
		return usersRepository.countByEmail(email);
	}

	public void toggleUserIsActive(final long id, final boolean isActive) {
		final Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User acitivity change fail"));
		user.setIsActive(isActive);
		usersRepository.save(user);
	}

	public void changeUserRole(final long id, final UserRoleEnum userRole) {
		final Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User role change fail"));
		user.setUserRole(userRole);
		usersRepository.save(user);
	}

	public void removeUser(final long id) {
		usersRepository.deleteById(id);
	}

	public boolean verifyPasswordPasscode(final String email, final String passcode) {
		Optional<UserPasswordPasscode> userPasswordPasscodeOptional = userPasswordPasscodeRepository.findByEmail(email);
		if (userPasswordPasscodeOptional.isPresent())
			if (userPasswordPasscodeOptional.get().getPasscode().equals(passcode))
				if (new Date().compareTo(userPasswordPasscodeOptional.get().getExpiry()) <= 0)
					return true;
		return false;
	}

	public void generatePasswordPasscode(final String email) {
		final Random random = new Random();
		final StringBuffer stringBuffer = new StringBuffer();
		for (short i = 0; i < 8; ++i)
			stringBuffer.append(random.nextInt(10));
		final Optional<UserPasswordPasscode> userPasswordPasscodeOptional = userPasswordPasscodeRepository.findByEmail(email);
		if (userPasswordPasscodeOptional.isPresent()) {
			final UserPasswordPasscode userPasswordPasscode = userPasswordPasscodeOptional.get();
			userPasswordPasscode.setPasscode(stringBuffer.toString());
			userPasswordPasscode.setExpiry(new Date(new Date().getTime() + 300000));
			if (userPasswordPasscode.getUser() == null)
				userPasswordPasscode.setUser(usersRepository.findByEmail(email).orElse(null));
			userPasswordPasscodeRepository.save(userPasswordPasscode);
		}
		else {
			final UserPasswordPasscode userPasswordPasscode = new UserPasswordPasscode();
			userPasswordPasscode.setEmail(email);
			userPasswordPasscode.setPasscode(stringBuffer.toString());
			userPasswordPasscode.setExpiry(new Date(new Date().getTime() + 300000));
			userPasswordPasscode.setUser(usersRepository.findByEmail(email).orElse(null));
			userPasswordPasscodeRepository.save(userPasswordPasscode);
		}

		emailService.sendReminder(email, "CRM - Email Passcode", "Passcode: " + stringBuffer);
	}
}
