package com.hlbs.crm.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
		attributes.put("roles", Arrays.stream(UserRoleEnum.values()).collect(Collectors.toList()));
		return "users/register";
	}

	@PostMapping(path = "register")
	public String registerUser(@ModelAttribute final UsersDTO usersDTO, final RedirectAttributes redirectAttributes) {
		usersService.addUser(usersDTO);
		redirectAttributes.addFlashAttribute("message", "Login here!");
		return "redirect:/users/login";
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
