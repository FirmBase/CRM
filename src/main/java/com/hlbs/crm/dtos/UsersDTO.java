package com.hlbs.crm.dtos;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlbs.crm.enumerations.UserRoleEnum;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UsersDTO {
	private Long id;

	private String email;

	private String password;

	private String firstName;

	private String middleName;

	private String lastName;

	private UserRoleEnum userRole;

	private Date registeredAt;

	private Boolean isActive;

	private List<UserPasswordPasscodeDTO> userPasswordPasscodeDTOs;

	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		}
		catch (JsonProcessingException jsonProcessingException) {
			jsonProcessingException.printStackTrace();
		}
		return super.toString();
	}
}
