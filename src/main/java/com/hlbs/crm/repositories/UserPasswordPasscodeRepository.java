package com.hlbs.crm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hlbs.crm.entities.UserPasswordPasscode;

@Repository
public interface UserPasswordPasscodeRepository extends JpaRepository<UserPasswordPasscode, Long> {
	public Optional<UserPasswordPasscode> findByEmail(final String email);
}
