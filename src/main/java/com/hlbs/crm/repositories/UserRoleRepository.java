package com.hlbs.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hlbs.crm.entities.Users;

@Repository
public interface UserRoleRepository extends JpaRepository<Users, Long> {
}
