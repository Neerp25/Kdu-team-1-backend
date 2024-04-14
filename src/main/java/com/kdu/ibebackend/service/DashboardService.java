package com.kdu.ibebackend.service;

import com.kdu.ibebackend.constants.graphql.GraphQLFetch;
import com.kdu.ibebackend.dto.TenantLoginCreds;
import com.kdu.ibebackend.dto.graphql.BookingStatus;
import com.kdu.ibebackend.dto.response.DashboardBookings;
import com.kdu.ibebackend.dto.response.DashboardResponse;
import com.kdu.ibebackend.entities.BookingExtensionMapper;
import com.kdu.ibebackend.entities.Tenant;
import com.kdu.ibebackend.repository.TenantRepository;
import com.kdu.ibebackend.exceptions.custom.InvalidTenantCreds;
import com.kdu.ibebackend.repository.BookingExtensionMapperRepository;
import com.kdu.ibebackend.utils.GraphUtils;
import com.kdu.ibebackend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
@Slf4j
public class DashboardService {
    private final TenantRepository tenantRepository;
    private BookingExtensionMapperRepository bookingExtensionMapperRepository;
    private GraphQLService graphQLService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    DashboardService(BookingExtensionMapperRepository bookingExtensionMapperRepository, GraphQLService graphQLService,
                     TenantRepository tenantRepository, PasswordEncoder passwordEncoder) {
        this.bookingExtensionMapperRepository = bookingExtensionMapperRepository;
        this.graphQLService = graphQLService;
        this.tenantRepository = tenantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Long> getBookingCount() {
        List<BookingExtensionMapper> bookings = bookingExtensionMapperRepository.findAll();

        Map<Integer, Long> totalBookingsByMonth = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        for (int month = 1; month <= 12; month++) {
            totalBookingsByMonth.put(month, 0L);
        }

        for (BookingExtensionMapper booking : bookings) {
            Calendar bookingCalendar = Calendar.getInstance();
            bookingCalendar.setTime(booking.getCreatedAt());
            int bookingYear = bookingCalendar.get(Calendar.YEAR);

            String query = GraphQLFetch.getBookingStatus;
            String injectedQuery = GraphUtils.injectUpdateBooking(query, booking.getBookingId());
            BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();

            if (bookingYear == currentYear && bookingStatus.getRes().getGetBooking().getStatusId() != 2) {
                int month = bookingCalendar.get(Calendar.MONTH) + 1;

                totalBookingsByMonth.put(month, totalBookingsByMonth.get(month) + 1);
            }
        }

        List<Long> bookingCount = new ArrayList<>(totalBookingsByMonth.values());

        return bookingCount;
    }

    public List<Double> getGraphData() {
        List<BookingExtensionMapper> bookings = bookingExtensionMapperRepository.findAll();

        Map<Integer, Double> totalTransactionsByMonth = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        for (int month = 1; month <= 12; month++) {
            totalTransactionsByMonth.put(month, 0.0);
        }

        for (BookingExtensionMapper booking : bookings) {
            Calendar bookingCalendar = Calendar.getInstance();
            bookingCalendar.setTime(booking.getCreatedAt());
            int bookingYear = bookingCalendar.get(Calendar.YEAR);

            String query = GraphQLFetch.getBookingStatus;
            String injectedQuery = GraphUtils.injectUpdateBooking(query, booking.getBookingId());
            BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();

            if (bookingYear == currentYear && bookingStatus.getRes().getGetBooking().getStatusId() != 2) {
                int month = bookingCalendar.get(Calendar.MONTH) + 1;

                Double totalTransaction = booking.getTransactionInfo().getTotal();

                totalTransactionsByMonth.put(month, totalTransactionsByMonth.get(month) + totalTransaction);
            }
        }

        List<Double> graphData = new ArrayList<>(totalTransactionsByMonth.values());

        return graphData;
    }

    public Double getTotalRevenue() {
        List<BookingExtensionMapper> bookings = bookingExtensionMapperRepository.findAll();

        return bookings.stream()
                .filter(booking -> {
                    String query = GraphQLFetch.getBookingStatus;
                    String injectedQuery = GraphUtils.injectUpdateBooking(query, booking.getBookingId());
                    BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();
                    return bookingStatus.getRes().getGetBooking().getStatusId() == 1;
                })
                .mapToDouble(booking -> booking.getTransactionInfo().getTotal())
                .sum();
    }

    public Long getTotalBookings() {
        return bookingExtensionMapperRepository.count();
    }

    public DashboardResponse getDashBoardResponse() {
        DashboardResponse dashboardResponse = new DashboardResponse();
        dashboardResponse.setGraph(getGraphData());
        dashboardResponse.setTotalBookingCount(getTotalBookings());
        dashboardResponse.setTotalRevenue(getTotalRevenue());

        List<Double> graphData = getGraphData();
        List<Long> bookingData = getBookingCount();

        double revenueIncrease = 0.0;
        int currentMonthIndex = LocalDate.now().getMonthValue() - 1;
        int previousMonthIndex = currentMonthIndex - 1;

        if (previousMonthIndex >= 0) {
            double currentMonthRevenue = graphData.get(currentMonthIndex);
            double previousMonthRevenue = graphData.get(previousMonthIndex);

            if (previousMonthRevenue != 0.0) {
                revenueIncrease = ((currentMonthRevenue - previousMonthRevenue) / Math.abs(previousMonthRevenue)) * 100;
            } else if (previousMonthRevenue == 0.0 && currentMonthRevenue != 0.0) {
                revenueIncrease += 100;
            }
        }

        double bookingIncrease = 0.0;
        long currentMonthBooking = bookingData.get(currentMonthIndex);
        long previousMonthBooking = previousMonthIndex >= 0 ? bookingData.get(previousMonthIndex) : 0;

        if (previousMonthBooking != 0) {
            bookingIncrease = ((currentMonthBooking - previousMonthBooking) / (double) Math.abs(previousMonthBooking)) * 100;
        } else if (previousMonthBooking == 0 && currentMonthBooking != 0) {
            bookingIncrease += 100;
        }

        dashboardResponse.setBookingIncrease(bookingIncrease);
        dashboardResponse.setRevenueIncrease(revenueIncrease);
        dashboardResponse.setCurrentMonthBookingCount(bookingData.get(currentMonthIndex));

        return dashboardResponse;
    }

    public List<DashboardBookings> getLatestBookings() {
        YearMonth currentYearMonth = YearMonth.now();

        LocalDate startDate = currentYearMonth.atDay(1);

        LocalDate endDate = currentYearMonth.atEndOfMonth();

        Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.of(startDate, LocalDateTime.MIN.toLocalTime()));
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.of(endDate, LocalDateTime.MAX.toLocalTime()));

        List<BookingExtensionMapper> res = bookingExtensionMapperRepository.findByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(endTimestamp, startTimestamp);
        List<DashboardBookings> latestBookings = new ArrayList<>();

        for (BookingExtensionMapper booking : res) {
            String query = GraphQLFetch.getBookingStatus;
            String injectedQuery = GraphUtils.injectUpdateBooking(query, booking.getBookingId());
            BookingStatus bookingStatus = graphQLService.executePostRequest(injectedQuery, BookingStatus.class).getBody();

            if (bookingStatus.getRes().getGetBooking().getStatusId() == 1) {
                latestBookings.add(new DashboardBookings(booking.getBillingInfo().getFirstName(), booking.getBillingInfo().getEmail(), booking.getTransactionInfo().getTotal(), false));
            } else {
                latestBookings.add(new DashboardBookings(booking.getBillingInfo().getFirstName(), booking.getBillingInfo().getEmail(), booking.getTransactionInfo().getTotal(), true));
            }
        }

        return latestBookings;
    }

    public String verifyCreds(TenantLoginCreds tenantLoginCreds) throws InvalidTenantCreds {
        Optional<Tenant> tenant = tenantRepository.findByEmailEquals(tenantLoginCreds.getEmail());
        if(tenant.isEmpty()) throw new InvalidTenantCreds("Tenant doesn't exist");

        if(!passwordEncoder.matches(tenantLoginCreds.getPassword(), tenant.get().getPassword())) throw new InvalidTenantCreds("Wrong Password");

        return tenantToken(tenantLoginCreds.getEmail());
    }

    public String tenantToken(String tenantEmail) {
        return JwtUtils.generateTenantToken(tenantEmail);
    }
}