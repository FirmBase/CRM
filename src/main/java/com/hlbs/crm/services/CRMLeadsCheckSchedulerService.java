package com.hlbs.crm.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.hlbs.crm.repositories.CRMInquiriesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CRMLeadsCheckSchedulerService {
	@Autowired
	private CRMInquiriesRepository crmInquiriesRepository;

	@Autowired
	private EmailService emailService;

	public void checkLeadsRecords() {
		crmInquiriesRepository.getAllIncompleteInquiryOrderByDueDateAsc().forEach(crmInquiry -> {
			final Date tenDaysLess = new Date(crmInquiry.getDueDate().getTime() - TimeUnit.DAYS.toMillis(10));
			if (new Date().compareTo(tenDaysLess) > 0) {
				System.out.println("This inquiry " + crmInquiry.getId() + " needs attention.");
				final StringBuilder emailBody = new StringBuilder();
				emailBody.append("Institution: " + crmInquiry.getInstitution() + "\n");
				if ((crmInquiry.getCustomerName() != null) && (!crmInquiry.getCustomerName().isEmpty()))
					emailBody.append("Customer: " + crmInquiry.getCustomerName() + "\n");
				if ((crmInquiry.getCustomerNumber() != null) && (!crmInquiry.getCustomerNumber().isEmpty()))
					emailBody.append("Phone: " + crmInquiry.getCustomerNumber() + "\n");
				if ((crmInquiry.getCustomerEmail() != null) && (!crmInquiry.getCustomerEmail().isEmpty()))
					emailBody.append("E-Mail: " + crmInquiry.getCustomerEmail() + "\n");
				emailBody.append("Products/Components: " + crmInquiry.getProductsOrComponents() + "\n");
				emailBody.append("Quantity: " + crmInquiry.getQuantity() + ", amount: " + crmInquiry.getPrice() * crmInquiry.getQuantity() + "\n");
				emailBody.append("Due date: " + new SimpleDateFormat("dd-MMM-yyyy").format(crmInquiry.getDueDate()) + "\n");
				try {
					emailService.sendReminder(crmInquiry.getSalesmanEmail(), "CRM - Due date is near for \"" + crmInquiry.getInstitution() + "\"", emailBody.toString());
				}
				catch (final MailException mailException) {
					System.out.println(mailException.getMessage());
					try {
						System.out.println("Mail failed to reach " + crmInquiry.getSalesmanEmail() + " trying again.");
						emailService.sendReminder(crmInquiry.getSalesmanEmail(), "CRM - Due date is near for \"" + crmInquiry.getInstitution() + "\"", emailBody.toString());
					}
					catch (final MailException mailException_) {
						System.out.println(mailException_.getMessage());
					}
				}
			}
		});
	}
}
