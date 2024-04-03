package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.ConcurrentTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcurrentTableRepository extends JpaRepository<ConcurrentTable, Integer> {
}