package com.hlbs.crm.dtos;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CRMInquiriesDTO {
	private Long id;

	private Date recordedAt;

	private String institution;

	private String productsOrComponents;

	private Integer quantity;

	private Double price;

	private String customerName;

	private String customerNumber;

	private String customerEmail;

	private Date dueDate;

	private String salesmanName;

	private String salesmanEmail;

	private Date lastUpdate;

	private String lastUpdateRemark;

	private Boolean orderReceived;

	private Boolean orderCompleted;

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		}
		catch (JsonProcessingException jsonProcessingException) {
			jsonProcessingException.printStackTrace();
		}
		return "";
	}
}
