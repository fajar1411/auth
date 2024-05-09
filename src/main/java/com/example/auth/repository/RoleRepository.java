package com.example.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
 @Query(value = "SELECT r from Role r WHERE r.name = ?1  ")
 public Role findRole(String name);
}
