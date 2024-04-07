package com.kdu.ibebackend.constants.graphql;

public class GraphQLMutations {
    public static String dummyMutation = "mutation MyMutation($guestName: String = \"Asish Mahapatra\") { updateGuest(where: {guest_id: 2}, data: {guest_name: $guestName}) { guest_id guest_name } }";
    public static String guestMutation = "mutation MyMutation($guestName: String = \"Asish\") { createGuest(data: {guest_name: $guestName}) { guest_id guest_name } }";
    public static String createBookingMutation = "mutation MyMutation($adultCount: Int = 10, $amountDueAtResort: Int = 10, $statusId: Int = 1, $checkInDate: AWSDateTime = \"2024-04-01T00:00:00.000Z\", $checkOutDate: AWSDateTime = \"2024-04-03T00:00:00.000Z\", $childCount: Int = 10, $guestId: Int = 1, $promotionId: Int = 1, $totalCost: Int = 10) { createBooking( data: {check_in_date: $checkInDate, check_out_date: $checkOutDate, adult_count: $adultCount, child_count: $childCount, total_cost: $totalCost, amount_due_at_resort: $amountDueAtResort, booking_status: {connect: {status_id: $statusId}}, guest: {connect: {guest_id: $guestId}}, property_booked: {connect: {property_id: 1}}, promotion_applied: {connect: {promotion_id: $promotionId}}}) { promotion_id booking_id }}";
    public static String updateAvailability = "mutation MyMutation($bookingId: Int = 0, $availabilityId: Int = 1) { updateRoomAvailability( where: {availability_id: $availabilityId} data: {booking: {connect: {booking_id: $bookingId}}}) { availability_id }}";
    public static String updateBooking = "mutation MyMutation($bookingId: Int = 0) { updateBooking( where: {booking_id: $bookingId} data: {booking_status: {connect: {status_id: 2}}} ) { booking_id } }";
}
