package com.hlbs.crm.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "CRM_INQUIRIES")
@Slf4j
public class CRMInquiries {
	@Id
	@Column(name = "ID", columnDefinition = "BIGINT UNSIGNED", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "RECORDED_AT", nullable = false)
	private Date recordedAt;

	@Column(name = "INSTITUTION", nullable = false, length = 64)
	private String institution;

	@Column(name = "PRODUCTS_OR_COMPONENTS", length = 255)
	private String productsOrComponents;

	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;

	@Column(name = "PRICE", nullable = false)
	private Double price;

	@Column(name = "CUSTOMER_NAME", length = 32)
	private String customerName;

	@Column(name = "CUSTOMER_NUMBER", length = 16)
	private String customerNumber;

	@Column(name = "CUSTOMER_EMAIL", length = 32)
	private String customerEmail;

	@Column(name = "DUE_DATE", nullable = false)
	private Date dueDate;

	@Column(name = "SALESMAN_NAME", nullable = false, length = 32)
	private String salesmanName;

	@Column(name = "SALESMAN_EMAIL", nullable = false, length = 32)
	private String salesmanEmail;

	@Column(name = "LAST_UPDATE", nullable = false)
	private Date lastUpdate;

	@Column(name = "LAST_UPDATE_REMARKS", nullable = true, length = 32)
	private String lastUpdateRemark;

	@Column(name = "ORDER_RECEIVED", nullable = false)
	private Boolean orderReceived;

	@Column(name = "ORDER_COMPLETED", nullable = false)
	private Boolean orderCompleted;
}
