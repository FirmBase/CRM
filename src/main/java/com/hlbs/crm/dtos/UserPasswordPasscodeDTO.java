package com.hlbs.crm.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class UserPasswordPasscodeDTO {
	private String email;

	private String passcode;

	private UsersDTO user;

	private Date expiry;
}
