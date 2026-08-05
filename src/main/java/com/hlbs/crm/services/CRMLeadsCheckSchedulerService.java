package com.hlbs.crm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlbs.crm.repositories.CRMInquiriesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CRMLeadsCheckSchedulerService {
	@Autowired
	private CRMInquiriesRepository crmInquiriesRepository;

	public void checkLeadsRecords() {
		crmInquiriesRepository.getAllIncompleteInquiryOrderByDueDateAsc().forEach(crmInquiry -> {});
	}
}
