package com.emir.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emir.securitydemo.model.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);
}
