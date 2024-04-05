package com.kdu.ibebackend.constants.graphql;

public class GraphQLFetch {
    public static String testQuery = "{countRooms}";
    public static String fetchProperties = "{ listProperties { property_name property_id } }";
    public static String basicNightlyRates = "{ listProperties (where: {property_id: {equals: 1}}){ room_type { room_rates { room_rate { basic_nightly_rate date room_rate_id } } } } }";
    public static String roomRes = "query MyQuery($bookingId: Int = 0, $startDate: AWSDateTime = \"2024-03-01T00:00:00.000Z\", $endDate: AWSDateTime = \"2024-03-02T00:00:00.000Z\") { listRoomAvailabilities( where: {booking_id: {equals: $bookingId}, date: {gte: $startDate, lte: $endDate}, property_id: {equals: 1}} orderBy: {date: ASC} ) { date room { room_id room_type_id room_type { area_in_square_feet double_bed max_capacity property_id room_type_name single_bed } } } }";
    public static String roomRateRoomTypeMappings = "query MyQuery($startDate: AWSDateTime = \"2024-03-01T00:00:00.000Z\", $endDate: AWSDateTime = \"2024-03-02T00:00:00.000Z\") { listRoomRateRoomTypeMappings( where: {room_rate: {date: {gte: $startDate, lte: $endDate}}, room_type: {property_id: {equals: 1}}} take: 1000000 ) { room_rate { basic_nightly_rate date } room_type { room_type_id room_type_name } } }";
    public static String promotionQuery = "query MyQuery { listPromotions { promotion_description promotion_id promotion_title price_factor minimum_days_of_stay } }";
}
