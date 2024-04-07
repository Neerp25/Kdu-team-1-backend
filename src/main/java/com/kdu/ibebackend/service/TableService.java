package com.kdu.ibebackend.service;

import com.kdu.ibebackend.dto.request.BookingDTO;
import com.kdu.ibebackend.entities.BillingInfo;
import com.kdu.ibebackend.entities.BookingExtensionMapper;
import com.kdu.ibebackend.entities.TransactionInfo;
import com.kdu.ibebackend.entities.TravelInfo;
import com.kdu.ibebackend.repository.BillingInfoRepository;
import com.kdu.ibebackend.repository.BookingExtensionMapperRepository;
import com.kdu.ibebackend.repository.TransactionInfoRepository;
import com.kdu.ibebackend.repository.TravelInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class TableService {
    private final BillingInfoRepository billingInfoRepository;
    private final TravelInfoRepository travelInfoRepository;
    private final BookingExtensionMapperRepository bookingExtensionMapperRepository;
    private final TransactionInfoRepository transactionInfoRepository;

    @Autowired
    public TableService(BillingInfoRepository billingInfoRepository, TravelInfoRepository travelInfoRepository, BookingExtensionMapperRepository bookingExtensionMapperRepository, TransactionInfoRepository transactionInfoRepository) {
        this.billingInfoRepository = billingInfoRepository;
        this.travelInfoRepository = travelInfoRepository;
        this.transactionInfoRepository = transactionInfoRepository;
        this.bookingExtensionMapperRepository = bookingExtensionMapperRepository;
    }

    public TravelInfo saveTravellerInfo(BookingDTO bookingDTO) {
        TravelInfo travelInfo = new TravelInfo();
        travelInfo.setFirstName(bookingDTO.getTravellerDTO().getFirstName());
        travelInfo.setLastName(bookingDTO.getTravellerDTO().getLastName());
        travelInfo.setEmail(bookingDTO.getTravellerDTO().getEmail());
        travelInfo.setPhoneNumber(bookingDTO.getTravellerDTO().getPhone());
        return travelInfoRepository.save(travelInfo);
    }

    public BillingInfo saveBillingInfo(BookingDTO bookingDTO) {
        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setFirstName(bookingDTO.getBillingDTO().getFirstName());
        billingInfo.setLastName(bookingDTO.getBillingDTO().getLastName());
        billingInfo.setPhoneNumber(bookingDTO.getBillingDTO().getPhone());
        billingInfo.setEmail(bookingDTO.getBillingDTO().getEmail());
        billingInfo.setCity(bookingDTO.getBillingDTO().getCity());
        billingInfo.setCountry(bookingDTO.getBillingDTO().getCountry());
        billingInfo.setZip(bookingDTO.getBillingDTO().getZipcode());
        billingInfo.setState(bookingDTO.getBillingDTO().getState());
        billingInfo.setMailingAddress1(bookingDTO.getBillingDTO().getMailingAddress1());
        billingInfo.setMailingAddress2(bookingDTO.getBillingDTO().getMailingAddress2());
        return billingInfoRepository.save(billingInfo);
    }

    public TransactionInfo saveTransactionInfo(BookingDTO bookingDTO) {
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setSubtotal(bookingDTO.getTransactionDTO().getSubtotal());
        transactionInfo.setVat(bookingDTO.getTransactionDTO().getVat());
        transactionInfo.setTaxes(bookingDTO.getTransactionDTO().getTaxes());
        transactionInfo.setNightlyRate(bookingDTO.getTransactionDTO().getNightlyRate());
        transactionInfo.setTotal(bookingDTO.getTransactionDTO().getTotal());
        return transactionInfoRepository.save(transactionInfo);
    }

    public BookingExtensionMapper saveBookingMapper(BookingDTO bookingDTO, Integer bookingId) {
        TravelInfo travelInfo = saveTravellerInfo(bookingDTO);
        BillingInfo billingInfo = saveBillingInfo(bookingDTO);
        TransactionInfo transactionInfo = saveTransactionInfo(bookingDTO);

        BookingExtensionMapper bookingExtensionMapper = new BookingExtensionMapper();
        bookingExtensionMapper.setBookingId(bookingId);
        bookingExtensionMapper.setBillingInfo(billingInfo);
        bookingExtensionMapper.setTravelInfo(travelInfo);
        bookingExtensionMapper.setTransactionInfo(transactionInfo);
        bookingExtensionMapper.setReservationId(UUID.randomUUID());
        bookingExtensionMapper.setRoomTypeId(bookingDTO.getBookingInfoDTO().getRoomTypeId());

        return bookingExtensionMapperRepository.save(bookingExtensionMapper);
    }

    public Optional<BookingExtensionMapper> findBooking(String reservationId) {
        UUID uuid = UUID.fromString(reservationId);
        return bookingExtensionMapperRepository.findByReservationIdEquals(uuid);
    }
}
