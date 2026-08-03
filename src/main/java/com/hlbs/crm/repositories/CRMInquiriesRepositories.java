package com.hlbs.crm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hlbs.crm.entities.CRMInquiries;

@Repository
public interface CRMInquiriesRepositories extends JpaRepository<CRMInquiries, Long> {
	List<CRMInquiries> findAllByOrderByDueDateAsc();
}
