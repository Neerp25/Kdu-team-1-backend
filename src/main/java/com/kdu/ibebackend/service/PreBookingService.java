package com.kdu.ibebackend.service;

import com.kdu.ibebackend.constants.Errors;
import com.kdu.ibebackend.dto.request.BookingDTO;
import com.kdu.ibebackend.entities.PreBookingTable;
import com.kdu.ibebackend.exceptions.custom.BookingException;
import com.kdu.ibebackend.repository.PreBookingTableRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PreBookingService {
    private PreBookingTableRepository preBookingTableRepository;

    @Autowired
    PreBookingService(PreBookingTableRepository preBookingTableRepository) {
        this.preBookingTableRepository = preBookingTableRepository;
    }

    @Transactional
    public List<PreBookingTable> batchInsert(List<PreBookingTable> preBookingTables) {
        return preBookingTableRepository.saveAll(preBookingTables);
    }

    public List<Integer> addToPreBookingTable(BookingDTO bookingDTO, List<Integer> roomIdsList) throws ParseException, ConstraintViolationException, BookingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date checkInDate = dateFormat.parse(bookingDTO.getBookingInfoDTO().getCheckInDate());
        Date checkOutDate = dateFormat.parse(bookingDTO.getBookingInfoDTO().getCheckOutDate());
        java.sql.Date checkInSqlDate = new java.sql.Date(checkInDate.getTime());
        java.sql.Date checkOutSqlDate = new java.sql.Date(checkOutDate.getTime());

        List<PreBookingTable> preBookingTables = new ArrayList<>();

        for (Integer roomId : roomIdsList) {
            PreBookingTable preBookingTable = new PreBookingTable();
            preBookingTable.setRoomId(roomId);
            preBookingTable.setStartDate(checkInSqlDate);
            preBookingTable.setEndDate(checkOutSqlDate);

            preBookingTables.add(preBookingTable);
        }

        List<Integer> insertedRoomIds = new ArrayList<>();

        int startIndex = 0;
        int roomsNeeded = bookingDTO.getBookingInfoDTO().getRooms();
        while (startIndex <= preBookingTables.size() - roomsNeeded) {
            try {
                List<PreBookingTable> insertions = batchInsert(preBookingTables.subList(startIndex, startIndex + roomsNeeded));
                log.info("Inserting one batch");
                for (PreBookingTable transaction : insertions) {
                    insertedRoomIds.add(transaction.getRoomId());
                }

                return insertedRoomIds;
            } catch (JpaSystemException ex) {
                log.info(ex.getMessage());
                if(ex.getMessage().contains("Overlapping dates for room ID")) {
                    log.info("Sliding Window");
                    startIndex++;
                }
                else {
                    throw new BookingException(Errors.BOOKING_ERROR);
                }
            }
        }

        if(startIndex>(preBookingTables.size() - roomsNeeded))
        {
            throw new BookingException(Errors.NO_ROOMS);
        }

        return new ArrayList<>();
    }
}
