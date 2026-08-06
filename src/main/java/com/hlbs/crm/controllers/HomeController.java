package com.hlbs.crm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(path = "")
@Slf4j
public class HomeController {
	@GetMapping(path = "")
	public String crmHome() {
		return "redirect:/crm/home";
	}
}
