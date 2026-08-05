package com.hlbs.crm.dtos;

import com.hlbs.crm.enumerations.UserRoleEnum;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class UserRoleDTO {
	private Long id;

	private UserRoleEnum userRole;
}
