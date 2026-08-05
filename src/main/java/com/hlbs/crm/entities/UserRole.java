package com.hlbs.crm.entities;

import com.hlbs.crm.enumerations.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "user_role")
@Slf4j
public class UserRole {
	@Id
	@Column(name = "ID", columnDefinition = "BIGINT UNSIGNED", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "USER_ROLE", nullable = false)
	private UserRoleEnum userRole;
}
