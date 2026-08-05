package com.hlbs.crm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hlbs.crm.entities.Users;
import com.hlbs.crm.enumerations.UserRoleEnum;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmailAndIsActive(final String email, final boolean isActive);
	List<Users> findAllByIsActive(final boolean isActive);
	Optional<Users> findByEmail(final String email);

	@Query(value = "SELECT users FROM Users users WHERE users.isActive = :isActive", nativeQuery = false)
	public List<Users> getAllUsersByActivity(final boolean isActive);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Users user SET user.isActive = :isActive WHERE user.id = :id", nativeQuery = false)
	public void toggleUserIsActive(final long id, final boolean isActive);

	@Modifying
	@Transactional
	@Query(value = "UPDATE Users user SET user.userRole = :userRole WHERE user.id = :id", nativeQuery = false)
	public void changeUserRole(final long id, final UserRoleEnum userRole);

	long countByEmail(final String email);
}
