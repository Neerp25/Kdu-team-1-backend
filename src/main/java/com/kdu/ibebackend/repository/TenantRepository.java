package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    Optional<Tenant> findByEmailEquals(String email);
}