package com.hlbs.crm.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(path = "error")
@Slf4j
public class ErrorController {
	@GetMapping(path = "home")
	public String crmHome(@RequestParam(name = "continue", required = false) final Boolean continue_, final Map<String, Object> attributes, final RedirectAttributes redirectAttributes) {
		attributes.put("continue", continue_);
		redirectAttributes.addFlashAttribute("message", continue_);
		return "redirect:/crm/home";
	}
}
