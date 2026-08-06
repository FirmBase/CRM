package com.hlbs.crm.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hlbs.crm.services.CRMLeadsCheckSchedulerService;

@Component
public class CRMLeadsCheckSchedulerTask {
	@Autowired
	private CRMLeadsCheckSchedulerService crmLeadsCheckSchedulerService;

	@Scheduled(fixedDelay = 86400000, initialDelay = 1000)
	public void checkLeadsRecords() {
		crmLeadsCheckSchedulerService.checkLeadsRecords();
	}
}
