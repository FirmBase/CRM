package com.hlbs.crm.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlbs.crm.enumerations.UserRoleEnum;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UserRoleDTO {
	private Long id;

	private UserRoleEnum userRole;

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
