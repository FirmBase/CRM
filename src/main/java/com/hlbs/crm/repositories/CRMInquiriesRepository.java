package com.hlbs.crm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hlbs.crm.entities.CRMInquiries;

@Repository
public interface CRMInquiriesRepository extends JpaRepository<CRMInquiries, Long> {
	public List<CRMInquiries> findAllByOrderByDueDateAsc();

	@Query(value = "SELECT inquiries FROM CRMInquiries inquiries ORDER BY CASE WHEN inquiries.orderReceived = true AND inquiries.orderCompleted = true THEN 1 ELSE 0 END, inquiries.dueDate ASC", nativeQuery = false)
	public List<CRMInquiries> getAllInquiryOrderByIncompleteDueDateAsc();

	@Query(value = "SELECT inquiries FROM CRMInquiries inquiries WHERE inquiries.orderReceived = false AND inquiries.orderCompleted = false ORDER BY inquiries.dueDate ASC", nativeQuery = false)
	public List<CRMInquiries> getAllIncompleteInquiryOrderByDueDateAsc();
}
