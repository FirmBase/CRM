package com.hlbs.crm.controllers;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webmvc.error.ErrorAttributes;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {
	private final ErrorAttributes errorAttributes;

	public CustomErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(path = "error")
	public String handleError(final HttpServletRequest httpServletRequest, final Model model) {
		// final Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        final WebRequest webRequest = new ServletWebRequest(httpServletRequest);

		final Map<String, Object> errorMap = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));

		model.addAllAttributes(errorMap);

		final Integer status = (Integer) errorMap.get("status");

		if (status != null) {
			final int statusCode = Integer.parseInt(status.toString());
			switch (statusCode) {
				case 401:
					return "errors/401";

				case 403:
					return "errors/403";

				case 404:
					return "errors/404";

				default:
					break;
			}
		}
		return "errors/error";
	}

	/*
	@GetMapping(path = "home")
	public String crmHome(@RequestParam(name = "continue", required = false) final Boolean continue_, final Map<String, Object> attributes, final RedirectAttributes redirectAttributes) {
		attributes.put("continue", continue_);
		redirectAttributes.addFlashAttribute("message", continue_);
		return "redirect:/crm/home";
	}
	*/
}
