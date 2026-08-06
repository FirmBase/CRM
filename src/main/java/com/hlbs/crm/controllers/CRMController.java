package com.hlbs.crm.controllers;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hlbs.crm.dtos.CRMInquiriesDTO;
import com.hlbs.crm.services.CRMService;
import com.hlbs.crm.services.UsersService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(path = "crm")
@Slf4j
public class CRMController {
	@Autowired
	private CRMService crmService;

	@Autowired
	private UsersService usersService;

	@GetMapping(path = "home")
	public String crmHome(final Map<String, Object> attributes) {
		attributes.put("inquiries", crmService.getAllInquiryOrderByIncompleteDueDateAsc());
		attributes.put("users", usersService.getAllUsersByActivity(true));
		return "crm/home";
	}

	@PostMapping(path = "new")
	public String crmHome(@RequestParam("dueDate") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date dueDate, @RequestParam("institution") final String institution, @RequestParam("productsOrComponents") final String productsOrComponents, @RequestParam("quantity") final int quantity, @RequestParam("price") final double price, @RequestParam("salesmanName") final String salesmanName, @RequestParam("salesmanEmail") final String salesmanEmail, @RequestParam("customerName") final String customerName, @RequestParam("customerNumber") final String customerNumber, @RequestParam("customerEmail") final String customerEmail, @RequestParam("lastUpdateRemark") final String lastUpdateRemark, final RedirectAttributes redirectAttributes) {
		final CRMInquiriesDTO crmInquiriesDTO = new CRMInquiriesDTO(null, new Date(), institution, productsOrComponents, quantity, price, customerName, customerNumber, customerEmail, dueDate, salesmanName, salesmanEmail, new Date(), lastUpdateRemark, false, false);

		try {
			crmService.addNewInquiry(crmInquiriesDTO);
			redirectAttributes.addFlashAttribute("message", "New inquiry added");
		}
		catch (final DataAccessException dataAccessException) {
			redirectAttributes.addFlashAttribute("message", "New inquiry fail");
		}
		return "redirect:/crm/home";
	}

	@PostMapping(path = "update")
	@ResponseBody
	public ResponseEntity<String> crmHome(@RequestParam("inquiry-id") final long id, @RequestParam("inquiry-order-received") final boolean orderReceived, @RequestParam("inquiry-order-completed") final boolean orderCompleted, @RequestParam("inquiry-last-remark") final String lastUpdateRemark) {
		try {
			crmService.updateInquiry(id, orderReceived, orderCompleted, lastUpdateRemark);
			return ResponseEntity.ok("true");
		}
		catch (final DataAccessException dataAccessException) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("false");
		}
	}

	@PostMapping(path = "delete")
	public String crmHome(@RequestParam("inquiry-id") final long id, final RedirectAttributes redirectAttributes) {
		try {
			crmService.deleteInquiry(id);
			redirectAttributes.addFlashAttribute("message", "Update success");
		}
		catch (final DataAccessException dataAccessException) {
			redirectAttributes.addFlashAttribute("message", "Update fail");
		}
		return "redirect:/crm/home";
	}
}
