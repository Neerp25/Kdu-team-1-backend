//package com.kdu.ibebackend.service;
//
//import com.backend.backendservice.model.booking.BookingTransactionInformation;
//import com.backend.backendservice.repository.BookingTransactionRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//public class BatchOperationService {
//    private final BookingTransactionRepo bookingTransactionRepo;
//
//
//    @Autowired
//    public BatchOperationService(BookingTransactionRepo bookingTransactionRepo) {
//        this.bookingTransactionRepo = bookingTransactionRepo;
//    }
//    @Transactional
//    public List<BookingTransactionInformation> insert(List<BookingTransactionInformation> transactionInformations)
//    {
//        List<BookingTransactionInformation> bookingTransactionInformationList = bookingTransactionRepo.saveAll(transactionInformations);
//        if(!bookingTransactionInformationList.isEmpty())
//        {
//            return bookingTransactionInformationList;
//        }
//        return null;
//    }
//
//
//    public List<Integer> batchInsertBookingTransactions(List<BookingTransactionInformation> transactions, int roomsNeeded) {
//        int startIndex = 0;
//        List<Integer> insertedRoomIds = new ArrayList<>();
//
//        while (startIndex <= transactions.size() - roomsNeeded) {
//
//            try {
//                List<BookingTransactionInformation> transactionsToSave = transactions.subList(startIndex, startIndex + roomsNeeded);
//                List<BookingTransactionInformation> bookingTransactionInformationList = insert(transactionsToSave);
//                log.info("Batch inserted: " + transactionsToSave.size() + " transactions."+bookingTransactionInformationList.size()+" "+bookingTransactionInformationList);
//                for (BookingTransactionInformation transaction : bookingTransactionInformationList) {
//                    insertedRoomIds.add(transaction.getRoomID());
//                }
//                return insertedRoomIds;
//
//            } catch (Exception e) {
//                startIndex++;
//                log.info("error here: "+e.getMessage());
//                if (isOverlappingDatesException(e)) {
//                    log.warn("Overlapping dates detected. Increasing sliding window.");
//                } else {
//                    log.error("Error during batch insert: " + e.getMessage(), e);
////                    throw new RuntimeException("Error during batch insert: " + e.getMessage());
//                }
//            }
//        }
//        log.info("startindex: "+startIndex+" "+(transactions.size()-roomsNeeded));
//        if(startIndex>(transactions.size() - roomsNeeded))
//        {
//            throw new RuntimeException("Unable to find enough rooms");
//        }
//        return new ArrayList<>();
//    }
//    public void deleteMatchingEntries(List<BookingTransactionInformation> transactionsToDelete) {
//        bookingTransactionRepo.deleteAll(transactionsToDelete);
//    }
//
//    private boolean isOverlappingDatesException(Exception e) {
//        if(e.getMessage().contains("conflicts with existing key"))
//        {
//            log.info("value of error:" +e.getMessage());
//            return true;
//        }
//        return false;
//    }
//}