package com.hlbs.crm.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlbs.crm.controllers.NotificationController;
import com.hlbs.crm.dtos.CRMInquiriesDTO;
import com.hlbs.crm.entities.CRMInquiries;
import com.hlbs.crm.repositories.CRMInquiriesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CRMService {
	@Autowired
	private CRMInquiriesRepository crmInquiriesRepository;

	@Autowired
	private NotificationController notificationController;

	public List<CRMInquiriesDTO>getAllInquiries() {
		final List<CRMInquiriesDTO> crmInquiriesDTOs = new ArrayList<CRMInquiriesDTO>();
		crmInquiriesRepository.findAll().forEach(crmInquiry -> {
			final CRMInquiriesDTO crmInquiriesDTO = new CRMInquiriesDTO();
			BeanUtils.copyProperties(crmInquiry, crmInquiriesDTO);
			crmInquiriesDTOs.add(crmInquiriesDTO);
		});
		return crmInquiriesDTOs;
	}

	public List<CRMInquiriesDTO> getAllInquiriesByOrderByDueDateAsc() {
		final List<CRMInquiriesDTO> crmInquiriesDTOs = new ArrayList<CRMInquiriesDTO>();
		crmInquiriesRepository.findAllByOrderByDueDateAsc().forEach(crmInquiry -> {
			final CRMInquiriesDTO crmInquiriesDTO = new CRMInquiriesDTO();
			BeanUtils.copyProperties(crmInquiry, crmInquiriesDTO);
			crmInquiriesDTOs.add(crmInquiriesDTO);
		});
		return crmInquiriesDTOs;
	}

	public List<CRMInquiriesDTO> getAllInquiryOrderByIncompleteDueDateAsc() {
		final List<CRMInquiriesDTO> crmInquiriesDTOs = new ArrayList<CRMInquiriesDTO>();
		crmInquiriesRepository.getAllInquiryOrderByIncompleteDueDateAsc().forEach(crmInquiry -> {
			final CRMInquiriesDTO crmInquiriesDTO = new CRMInquiriesDTO();
			BeanUtils.copyProperties(crmInquiry, crmInquiriesDTO);
			crmInquiriesDTOs.add(crmInquiriesDTO);
		});
		return crmInquiriesDTOs;
	}

	public List<CRMInquiriesDTO> getAllIncompleteInquiryOrderByDueDateAsc() {
		final List<CRMInquiriesDTO> crmInquiriesDTOs = new ArrayList<CRMInquiriesDTO>();
		crmInquiriesRepository.getAllIncompleteInquiryOrderByDueDateAsc().forEach(crmInquiry -> {
			final CRMInquiriesDTO crmInquiriesDTO = new CRMInquiriesDTO();
			BeanUtils.copyProperties(crmInquiry, crmInquiriesDTO);
			crmInquiriesDTOs.add(crmInquiriesDTO);
		});
		return crmInquiriesDTOs;
	}

	public void addNewInquiry(final CRMInquiriesDTO crmInquiriesDTO) {
		final CRMInquiries crmInquiries = new CRMInquiries();
		BeanUtils.copyProperties(crmInquiriesDTO, crmInquiries);
		crmInquiriesRepository.saveAndFlush(crmInquiries);

		notificationController.notifyClients("New inquiry added");
	}

	public void updateInquiry(final long inquiryId, final boolean inquiryOrderReceived, final boolean inquiryOrderCompleted, final String inquiryLastUpdateRemark) {
		final CRMInquiries crmInquiries = crmInquiriesRepository.findById(inquiryId).orElseThrow();
		crmInquiries.setOrderReceived(inquiryOrderReceived);
		if (inquiryOrderCompleted)
			crmInquiries.setOrderReceived(true);
		crmInquiries.setOrderCompleted(inquiryOrderCompleted);
		crmInquiries.setLastUpdateRemark(inquiryLastUpdateRemark);
		crmInquiries.setLastUpdate(new Date());
		crmInquiriesRepository.saveAndFlush(crmInquiries);

		notificationController.notifyClients("New inquiry added");
	}

	public void deleteInquiry(final long inquiryId) {
		crmInquiriesRepository.deleteById(inquiryId);

		notificationController.notifyClients("Inquiry " + inquiryId + " deleted.");
	}
}
