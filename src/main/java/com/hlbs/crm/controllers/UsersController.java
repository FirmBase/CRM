package com.hlbs.crm.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hlbs.crm.dtos.UsersDTO;
import com.hlbs.crm.enumerations.UserRoleEnum;
import com.hlbs.crm.services.UsersService;

@Controller
@RequestMapping(path = "users")
public class UsersController {
	@Autowired
	private UsersService usersService;

	@GetMapping(path = "login")
	public String loginUser(@RequestParam(name = "error", required = false) final Boolean error, final RedirectAttributes redirectAttributes) {
		if ((error != null) && error) {
			redirectAttributes.addFlashAttribute("message", "Login failed!");
			return "redirect:/users/login";
		}
		return "users/login";
	}

	@GetMapping(path = "register")
	public String registerUser(final Map<String, Object> attributes) {
		// attributes.put("roles", Arrays.stream(UserRoleEnum.values()).filter(userRoleEnum -> userRoleEnum != UserRoleEnum.ADMIN).collect(Collectors.toList()));
		attributes.put("name_display", true);
		attributes.put("first_name_value", "");
		attributes.put("middle_name_value", "");
		attributes.put("last_name_value", "");
		attributes.put("email_display", true);
		attributes.put("email_readonly", false);
		attributes.put("email_value", "");
		attributes.put("passcode_display", false);
		attributes.put("passcode_value", "");
		attributes.put("passcode_readonly", false);
		attributes.put("password_display", false);
		attributes.put("role_display", false);
		attributes.put("register_display", true);
		return "users/register";
	}

	@PostMapping(path = "register")
	public String registerUser(@ModelAttribute final UsersDTO usersDTO, @RequestParam(name = "passcode", required = false) final String passcode, final Map<String, Object> attributes, final RedirectAttributes redirectAttributes) {
		if (passcode == null) {
			usersService.generatePasswordPasscode(usersDTO.getEmail());
			attributes.put("message", "Passcode is sent to your e-mail.");
			attributes.put("name_display", true);
			attributes.put("first_name_value", usersDTO.getFirstName());
			attributes.put("middle_name_value", usersDTO.getMiddleName());
			attributes.put("last_name_value", usersDTO.getLastName());
			attributes.put("email_display", false);
			attributes.put("email_readonly", true);
			attributes.put("email_value", usersDTO.getEmail());
			attributes.put("passcode_display", true);
			attributes.put("passcode_value", "");
			attributes.put("passcode_readonly", false);
			attributes.put("password_display", false);
			attributes.put("role_display", false);
			attributes.put("register_display", false);
			return "users/register";
		}
		else {
			if (Stream.of(usersDTO.getFirstName(), usersDTO.getLastName(), usersDTO.getEmail(), usersDTO.getPassword(), usersDTO.getUserRole()).allMatch(Objects::nonNull))
				if (usersService.verifyPasswordPasscode(usersDTO.getEmail(), passcode)) {
					usersService.addUser(usersDTO);
					redirectAttributes.addFlashAttribute("message", "Registration successful, signin");
					return "redirect:/users/login";
				}
				else {
					redirectAttributes.addFlashAttribute("message", "Email verification failed.");
					return "redirect:/users/register";
				}
			else
				if (usersService.verifyPasswordPasscode(usersDTO.getEmail(), passcode)) {
					attributes.put("roles", Arrays.stream(UserRoleEnum.values()).filter(userRoleEnum -> userRoleEnum != UserRoleEnum.ADMIN).collect(Collectors.toList()));
					attributes.put("name_display", true);
					attributes.put("first_name_value", usersDTO.getFirstName());
					attributes.put("middle_name_value", usersDTO.getMiddleName());
					attributes.put("last_name_value", usersDTO.getLastName());
					attributes.put("email_display", true);
					attributes.put("email_readonly", false);
					attributes.put("email_value", usersDTO.getEmail());
					attributes.put("passcode_display", true);
					attributes.put("passcode_value", passcode);
					attributes.put("passcode_readonly", true);
					attributes.put("password_display", true);
					attributes.put("role_display", true);
					attributes.put("register_display", true);
					return "users/register";
				}
				else {
					redirectAttributes.addFlashAttribute("message", "Email verification failed.");
					return "redirect:/users/register";
				}
		}
		// usersService.addUser(usersDTO);
		// redirectAttributes.addFlashAttribute("message", "Login here!");
		// return "redirect:/users/login";
	}

	@GetMapping(path = "reset-password")
	public String resetPassword(final Map<String, Object> attributes) {
		attributes.put("email_display", true);
		attributes.put("email_readonly", false);
		attributes.put("email_value", "");
		attributes.put("passcode_display", false);
		attributes.put("passcode_value", "");
		attributes.put("passcode_readonly", false);
		attributes.put("password_display", false);
		attributes.put("register_display", true);
		return "users/reset_password";
	}

	@PostMapping(path = "reset-password")
	public String resetPassword(@ModelAttribute final UsersDTO usersDTO, @RequestParam(name = "passcode", required = false) final String passcode, final Map<String, Object> attributes, final RedirectAttributes redirectAttributes) {
		if (passcode == null) {
			usersService.generatePasswordPasscode(usersDTO.getEmail());
			attributes.put("email_display", true);
			attributes.put("email_readonly", true);
			attributes.put("email_value", usersDTO.getEmail());
			attributes.put("passcode_display", true);
			attributes.put("passcode_value", passcode);
			attributes.put("passcode_readonly", false);
			attributes.put("password_display", false);
			attributes.put("register_display", false);
			attributes.put("message", "Passcode sent to email");
			return "users/reset_password";
		}
		else if ((usersDTO.getPassword() != null) && (!usersDTO.getPassword().isEmpty())) {
			if (usersService.verifyPasswordPasscode(usersDTO.getEmail(), passcode))
				if (usersService.changePassword(usersDTO.getEmail(), usersDTO.getPassword()))
					redirectAttributes.addFlashAttribute("message", "Password changed");
				else
					redirectAttributes.addFlashAttribute("message", "Password change failed");
			else
				redirectAttributes.addFlashAttribute("message", "Passcode verification failed");
			return "redirect:/users/login";
		}
		else {
			attributes.put("email_display", true);
			attributes.put("email_readonly", true);
			attributes.put("email_value", usersDTO.getEmail());
			attributes.put("passcode_display", true);
			attributes.put("passcode_value", passcode);
			attributes.put("passcode_readonly", true);
			attributes.put("password_display", true);
			attributes.put("register_display", true);
			return "users/reset_password";
		}
	}

	@GetMapping(path = "manage")
	public String allUsers(final Map<String, Object> attributes, final Principal principal) {
		attributes.put("users", usersService.getAllUsers().stream().filter(role -> role.getId() != usersService.getIdByEmail(principal.getName())).collect(Collectors.toList()));
		attributes.put("roles", Arrays.stream(UserRoleEnum.values()).collect(Collectors.toList()));
		return "users/home";
	}

	@PostMapping(path = "remove")
	public String allUsers(@RequestParam("id") final long id, final RedirectAttributes redirectAttributes) {
		usersService.removeUser(id);
		redirectAttributes.addFlashAttribute("message", "User removed");
		return "redirect:/users/manage";
	}

	@PostMapping(path = "toggle")
	public String allUsers(@RequestParam("id") final long id, @RequestParam("isActive") final boolean isActive, final RedirectAttributes redirectAttributes) {
		usersService.toggleUserIsActive(id, !isActive);
		if (isActive)
			redirectAttributes.addFlashAttribute("message", "User is inactive");
		else
			redirectAttributes.addFlashAttribute("message", "User is active");
		return "redirect:/users/manage";
	}

	@PostMapping(path = "role")
	public String allUsers(@RequestParam("id") final long id, @RequestParam("userRole") final String userRole, final RedirectAttributes redirectAttributes) {
		usersService.changeUserRole(id, UserRoleEnum.valueOf(userRole));
		redirectAttributes.addFlashAttribute("message", "Role changed");
		return "redirect:/users/manage";
	}

	@ResponseBody
	@PostMapping(path = "email-available")
	public String emailAvailability(@RequestParam("email") final String email) {
		if (usersService.countByEmail(email) > 0)
			return "false";
		else
			return "true";
	}
}
