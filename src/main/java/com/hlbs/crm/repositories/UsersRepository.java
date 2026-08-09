package com.hlbs.crm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hlbs.crm.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	public Optional<Users> findByEmailAndIsActive(final String email, final boolean isActive);
	public List<Users> findAllByIsActive(final boolean isActive);
	public Optional<Users> findByEmail(final String email);

	@Query(value = "SELECT users FROM Users users WHERE users.isActive = :isActive", nativeQuery = false)
	public List<Users> getAllUsersByActivity(final boolean isActive);

	long countByEmail(final String email);
}
