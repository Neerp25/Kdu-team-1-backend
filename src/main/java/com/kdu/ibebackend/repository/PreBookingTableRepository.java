package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.PreBookingTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PreBookingTableRepository extends JpaRepository<PreBookingTable, Integer> {
    long deleteByRoomIdInAllIgnoreCase(Collection<Integer> roomIds);
}