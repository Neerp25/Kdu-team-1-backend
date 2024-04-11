package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.BookingExtensionMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingExtensionMapperRepository extends JpaRepository<BookingExtensionMapper, Integer> {
    Optional<BookingExtensionMapper> findByReservationIdEquals(UUID reservationId);

    List<BookingExtensionMapper> findByTravelInfo_EmailEquals(String email);
}