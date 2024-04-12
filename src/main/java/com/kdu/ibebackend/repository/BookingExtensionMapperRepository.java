package com.kdu.ibebackend.repository;

import com.kdu.ibebackend.entities.BookingExtensionMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingExtensionMapperRepository extends JpaRepository<BookingExtensionMapper, Integer> {
    Optional<BookingExtensionMapper> findByReservationIdEquals(UUID reservationId);

    List<BookingExtensionMapper> findByTravelInfo_EmailEquals(String email);

    List<BookingExtensionMapper> findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(Timestamp endDate, Timestamp startDate);
}