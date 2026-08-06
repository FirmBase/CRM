package com.hlbs.crm.entities;

import java.util.Date;

import com.hlbs.crm.enumerations.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "users")
@Slf4j
public class Users {
	@Id
	@Column(name = "ID", columnDefinition = "BIGINT UNSIGNED", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "EMAIL", nullable = false, unique = true, length = 32)
	private String email;

	@Column(name = "PASSWORD", nullable = false, length = 64)
	private String password;

	@Column(name = "FIRST_NAME", nullable = false, length = 16)
	private String firstName;

	@Column(name = "MIDDLE_NAME", nullable = true, length = 16)
	private String middleName;

	@Column(name = "LAST_NAME", nullable = false, length = 16)
	private String lastName;

	/*
	@ManyToOne
	@JoinColumn(name = "USER_ROLE", referencedColumnName = "ID", nullable = false)
	private UserRole userRole;
	*/

	@Column(name = "ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRoleEnum userRole;

	@Column(name = "REGISTERED_AT", nullable = false)
	private Date registeredAt;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;
}
