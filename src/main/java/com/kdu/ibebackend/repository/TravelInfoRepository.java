package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.TravelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelInfoRepository extends JpaRepository<TravelInfo, Integer> {
}