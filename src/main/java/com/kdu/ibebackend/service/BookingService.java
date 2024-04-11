package com.kdu.ibebackend.service;

import com.kdu.ibebackend.constants.EmailTemplate;
import com.kdu.ibebackend.constants.Errors;
import com.kdu.ibebackend.constants.graphql.GraphQLFetch;
import com.kdu.ibebackend.constants.graphql.GraphQLMutations;
import com.kdu.ibebackend.dto.graphql.*;
import com.kdu.ibebackend.dto.request.BookingDTO;
import com.kdu.ibebackend.dto.response.BookingResponse;
import com.kdu.ibebackend.dto.response.PersonalBooking;
import com.kdu.ibebackend.dto.response.RoomType;
import com.kdu.ibebackend.entities.BookingExtensionMapper;
import com.kdu.ibebackend.exceptions.custom.BookingException;
import com.kdu.ibebackend.models.dynamodb.RoomInfo;
import com.kdu.ibebackend.repository.BookingExtensionMapperRepository;
import com.kdu.ibebackend.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service to execute to all booking related functions along with Concurrency
 */
@Service
@Slf4j
public class BookingService {
    private final BookingExtensionMapperRepository bookingExtensionMapperRepository;
    private GraphQLService graphQLService;
    private TableService tableService;
    private DynamoDBService dynamoDBService;
    private EmailService emailService;
    private PreBookingService preBookingService;

    @Autowired
    public BookingService(GraphQLService graphQLService, TableService tableService, DynamoDBService dynamoDBService, EmailService emailService, PreBookingService preBookingService,
                          BookingExtensionMapperRepository bookingExtensionMapperRepository) {
        this.graphQLService = graphQLService;
        this.tableService = tableService;
        this.dynamoDBService = dynamoDBService;
        this.emailService = emailService;
        this.preBookingService = preBookingService;
        this.bookingExtensionMapperRepository = bookingExtensionMapperRepository;
    }

    public static Map<Integer, List<Integer>> getAvailableRooms(List<ListRoomAvailabilityIds.Availability> availabilityData, String startDateStr, String endDateStr) {
        Map<Integer, List<Integer>> availableRooms = new HashMap<>();

        LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10));
        LocalDate endDate = LocalDate.parse(endDateStr.substring(0, 10));

        Map<LocalDate, Set<Integer>> availabilityMap = new HashMap<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            availabilityMap.put(date, new HashSet<>());
        }

        for (ListRoomAvailabilityIds.Availability availability : availabilityData) {
            LocalDate date = LocalDate.parse(availability.getDate().substring(0, 10));
            int roomId = availability.getRoomId();
            availabilityMap.get(date).add(roomId);
        }

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (Map.Entry<LocalDate, Set<Integer>> entry : availabilityMap.entrySet()) {
                if (entry.getKey().equals(date)) {
                    for (int roomId : entry.getValue()) {
                        availableRooms.computeIfAbsent(roomId, k -> new ArrayList<>());
                        LocalDate finalDate = date;
                        availableRooms.get(roomId).add(availabilityData.stream()
                                .filter(av -> av.getRoomId() == roomId && av.getDate().startsWith(finalDate.toString()))
                                .map(ListRoomAvailabilityIds.Availability::getRoomAvailabilityId)
                                .findFirst().orElse(-1));
                    }
                }
            }
        }

        availableRooms.entrySet().removeIf(entry -> entry.getValue().size() != endDate.getDayOfYear() - startDate.getDayOfYear() + 1);

        return availableRooms;
    }

    public UUID createFinalBooking(BookingDTO bookingDTO) throws BookingException, ParseException {
        String query = GraphQLFetch.roomAvailabilityIds;
        String injectedQuery = GraphUtils.injectRoomAvailabilityIdQuery(query, bookingDTO.getBookingInfoDTO().getCheckInDate(), bookingDTO.getBookingInfoDTO().getCheckOutDate(), bookingDTO.getBookingInfoDTO().getRoomTypeId());

        ListRoomAvailabilityIds listRoomAvailabilityIds = graphQLService.executePostRequest(injectedQuery, ListRoomAvailabilityIds.class).getBody();
        List<ListRoomAvailabilityIds.Availability> availabilityList = listRoomAvailabilityIds.getRes().getAvailabilities();

        Map<Integer, List<Integer>> availableRooms = getAvailableRooms(availabilityList, bookingDTO.getBookingInfoDTO().getCheckInDate(), bookingDTO.getBookingInfoDTO().getCheckOutDate());

        log.info(availableRooms.toString());
        List<Integer> roomIds = new ArrayList<>(availableRooms.keySet());
        log.info(roomIds.toString());

        if (availableRooms.size() < bookingDTO.getBookingInfoDTO().getRooms()) {
            throw new BookingException(Errors.NO_ROOMS);
        }

        List<Integer> insertedRoomIds = preBookingService.addToPreBookingTable(bookingDTO, roomIds);

        Integer guestId = createGuestEntry(bookingDTO);
        Integer bookingId = createBookingEntry(bookingDTO, guestId);
        updateAvailabilities(availableRooms, bookingId, insertedRoomIds);
        BookingExtensionMapper res = tableService.saveBookingMapper(bookingDTO, bookingId);

//        String templateData = EmailUtils.bookingEmailTemplateGenerator(res.getReservationId().toString());
//        emailService.sendTemplatedEmail(EmailTemplate.BOOKING_TEMPLATE_NAME, bookingDTO.getBillingDTO().getEmail(), templateData);

        return res.getReservationId();
    }

    public Integer createGuestEntry(BookingDTO bookingDTO) {
        String query = GraphQLMutations.guestMutation;
        String injectedQuery = GraphUtils.injectGuestMutationQuery(query, bookingDTO.getBookingInfoDTO().getGuestName());
        CreateGuest createGuest = graphQLService.executePostRequest(injectedQuery, CreateGuest.class).getBody();

        log.info(createGuest.getRes().getGuest().toString());
        return Integer.parseInt(createGuest.getRes().getGuest().getGuestId());
    }

    public Integer createBookingEntry(BookingDTO bookingDTO, Integer guestId) {
        String query = GraphQLMutations.createBookingMutation;
        String injectedQuery = GraphUtils.injectCreateBookingQuery(query, bookingDTO, guestId);
        CreateBooking createBooking = graphQLService.executePostRequest(injectedQuery, CreateBooking.class).getBody();

        log.info(createBooking.toString());
        return createBooking.getRes().getBooking().getBookingId();
    }

    public void updateAvailabilities(Map<Integer, List<Integer>> availableRooms, Integer bookingId, List<Integer> roomIds) {
        for (Integer roomId : roomIds) {
            List<Integer> availabilityIds = availableRooms.get(roomId);

            for (Integer availabilityId : availabilityIds) {
                String query = GraphQLMutations.updateAvailability;
                String injectedQuery = GraphUtils.injectUpdateAvailabilityQuery(query, bookingId, availabilityId);
                UpdateRoomAvailability updateRoomAvailability = graphQLService.executePostRequest(injectedQuery, UpdateRoomAvailability.class).getBody();
                log.info(updateRoomAvailability.toString());
            }
        }
    }

    public void deleteBooking(String reservationId) throws BookingException {
        Optional<BookingExtensionMapper> res = tableService.findBooking(reservationId);
        if (res.isEmpty()) throw new BookingException(Errors.NO_BOOKING);

        Integer bookingId = res.get().getBookingId();

        String query = GraphQLMutations.updateBooking;
        String injectedQuery = GraphUtils.injectUpdateBooking(query, bookingId);
        UpdateBooking updateBooking = graphQLService.executePostRequest(injectedQuery, UpdateBooking.class).getBody();

        String availabilityQuery = GraphQLFetch.roomAvailabilityPerBooking;
        String injectedAvailabilityQuery = GraphUtils.injectUpdateBooking(availabilityQuery, bookingId);
        ListRoomAvailabilityIds listRoomAvailabilityIds = graphQLService.executePostRequest(injectedAvailabilityQuery, ListRoomAvailabilityIds.class).getBody();

        for (ListRoomAvailabilityIds.Availability availability : listRoomAvailabilityIds.getRes().getAvailabilities()) {
            String updateQuery = GraphQLMutations.updateAvailability;
            String injectedUpdateQuery = GraphUtils.injectUpdateAvailabilityQuery(updateQuery, 0, availability.getRoomAvailabilityId());
            UpdateRoomAvailability updateRoomAvailability = graphQLService.executePostRequest(injectedUpdateQuery, UpdateRoomAvailability.class).getBody();
            log.info(updateRoomAvailability.toString());
        }

        preBookingService.clearPreBookingTable(listRoomAvailabilityIds);
    }

    public BookingResponse verifyBooking(String reservationId) {
        Optional<BookingExtensionMapper> res = tableService.findBooking(reservationId);
        if (res.isEmpty()) {
            return null;
        }

        Integer bookingId = res.get().getBookingId();
        BookingResponse bookingResponse = new BookingResponse();
        List<RoomType> roomTypes = getRoomTypes();

        String query = GraphQLFetch.getBookingStatus;
        String injectedQuery = GraphUtils.injectUpdateBooking(query, bookingId);
        BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();
        List<RoomInfo> roomInfoList = RoomUtils.findRoomInfo(dynamoDBService);

        log.info(bookingStatus.toString());
        log.info(roomTypes.toString());
        log.info(bookingId.toString());

        bookingResponse.setRoomTypeName(roomTypes.get(res.get().getRoomTypeId() - 1).getRoomTypeName());
        bookingResponse.setRoomTypeId(res.get().getRoomTypeId());
        bookingResponse.setReservationId(reservationId);

        Date checkIn = DateUtils.parseDateString(bookingStatus.getRes().getGetBooking().getCheckInDate());
        Date checkOut = DateUtils.parseDateString(bookingStatus.getRes().getGetBooking().getCheckOutDate());
        bookingResponse.setCheckInDate(checkIn);
        bookingResponse.setCheckOutDate(checkOut);

        bookingResponse.setImageUrl(roomInfoList.get(res.get().getRoomTypeId() - 1).getLowResImages().get(0));
        bookingResponse.setPromotionTitle(bookingStatus.getRes().getGetBooking().getPromotionType().getPromotionTitle());
        bookingResponse.setPromotionDescription(bookingStatus.getRes().getGetBooking().getPromotionType().getPromotionDescription());
        bookingResponse.setAdults(bookingStatus.getRes().getGetBooking().getAdultCount());
        bookingResponse.setChildren(bookingStatus.getRes().getGetBooking().getChildCount());
        bookingResponse.setNightlyRate(res.get().getTransactionInfo().getNightlyRate());
        bookingResponse.setSubtotal(res.get().getTransactionInfo().getSubtotal());
        bookingResponse.setTotal(bookingStatus.getRes().getGetBooking().getTotalCost());
        bookingResponse.setTaxes(res.get().getTransactionInfo().getTaxes());
        bookingResponse.setVat(res.get().getTransactionInfo().getVat());
        bookingResponse.setFirstName(res.get().getTravelInfo().getFirstName());
        bookingResponse.setLastName(res.get().getTravelInfo().getLastName());
        bookingResponse.setPhone(res.get().getTravelInfo().getPhoneNumber());
        bookingResponse.setEmail(res.get().getTravelInfo().getEmail());
        bookingResponse.setBillingFirstName(res.get().getBillingInfo().getFirstName());
        bookingResponse.setBillingLastName(res.get().getBillingInfo().getLastName());
        bookingResponse.setMailingAddress1(res.get().getBillingInfo().getMailingAddress1());
        bookingResponse.setMailingAddress2(res.get().getBillingInfo().getMailingAddress2());
        bookingResponse.setCountry(res.get().getBillingInfo().getCountry());
        bookingResponse.setCity(res.get().getBillingInfo().getCity());
        bookingResponse.setState(res.get().getBillingInfo().getState());
        bookingResponse.setZipcode(res.get().getBillingInfo().getZip());
        bookingResponse.setBillingPhone(res.get().getBillingInfo().getPhoneNumber());
        bookingResponse.setBillingEmail(res.get().getBillingInfo().getEmail());
        bookingResponse.setTransactionId(res.get().getReservationId().toString());

        if (bookingStatus.getRes().getGetBooking().getStatusId() == 1) {
            bookingResponse.setCancelled(false);
        } else if (bookingStatus.getRes().getGetBooking().getStatusId() == 2) {
            bookingResponse.setCancelled(true);
        }

        return bookingResponse;
    }

    @Cacheable("roomtypes")
    public List<RoomType> getRoomTypes() {
        String query = GraphQLFetch.getRoomTypes;
        String formattedQuery = GraphUtils.convertToGraphQLRequest(query);
        ListRoomTypes res = graphQLService.executePostRequest(formattedQuery, ListRoomTypes.class).getBody();
        return res.getRes().getRoomTypes();
    }

    public String bookingToken(String reservationId) {
        return JwtUtils.generateBookingToken(reservationId);
    }

    public List<PersonalBooking> fetchBookings(String email) {
        List<BookingExtensionMapper> bookingExtensionMappers = bookingExtensionMapperRepository.findByTravelInfo_EmailEquals(email);

        List<PersonalBooking> bookings = new ArrayList<>();

        for(BookingExtensionMapper booking : bookingExtensionMappers) {
            String query = GraphQLFetch.getBookingStatus;
            String injectedQuery = GraphUtils.injectUpdateBooking(query, booking.getBookingId());
            BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();

            PersonalBooking personalBooking = new PersonalBooking();
            personalBooking.setAdultCount(bookingStatus.getRes().getGetBooking().getAdultCount());
            personalBooking.setChildCount(bookingStatus.getRes().getGetBooking().getChildCount());
            personalBooking.setTotalCost(bookingStatus.getRes().getGetBooking().getTotalCost());
            personalBooking.setCheckInDate(bookingStatus.getRes().getGetBooking().getCheckInDate());
            personalBooking.setCheckOutDate(bookingStatus.getRes().getGetBooking().getCheckOutDate());

            if (bookingStatus.getRes().getGetBooking().getStatusId() == 1) {
                personalBooking.setStatus(false);
            } else if (bookingStatus.getRes().getGetBooking().getStatusId() == 2) {
                personalBooking.setStatus(true);
            }

            bookings.add(personalBooking);
        }

        return bookings;
    }
}
