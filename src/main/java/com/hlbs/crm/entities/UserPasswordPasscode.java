package com.hlbs.crm.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Entity
@Table(name = "USER_PASSWORD_PASSCODE")
@Slf4j
public class UserPasswordPasscode {
	@Id
	@Column(name = "EMAIL", nullable = false, unique = true, length = 64)
	private String email;

	@Column(name = "PASSCODE", nullable = false, unique = true, length = 8)
	private String passcode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", columnDefinition = "BIGINT UNSIGNED", referencedColumnName = "ID", nullable = true, unique = false)
	private Users user;

	@Column(name = "EXPIRY", nullable = false)
	private Date expiry;
}
