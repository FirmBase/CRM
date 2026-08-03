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
@Table(name = "crm_inquiries")
@Slf4j
public class CRMInquiries {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "RECORDED_AT")
	private Date recordedAt;

	@Column(name = "INSTITUTION")
	private String institution;

	@Column(name = "PRODUCTS_OR_COMPONENTS")
	private String productsOrComponents;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "CUSTOMER_NUMBER")
	private String customerNumber;

	@Column(name = "CUSTOMER_EMAIL")
	private String customerEmail;

	@Column(name = "DUE_DATE")
	private Date dueDate;

	@Column(name = "SALESMAN_NAME")
	private String salesmanName;

	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;

	@Column(name = "LAST_UPDATE_REMARKS")
	private String lastUpdateRemark;

	@Column(name = "ORDER_RECEIVED")
	private Boolean orderReceived;

	@Column(name = "ORDER_COMPLETED")
	private Boolean orderCompleted;
}
