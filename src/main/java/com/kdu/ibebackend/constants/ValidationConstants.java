package com.kdu.ibebackend.constants;

/**
 * Messages for DTO Level validation of Request Body
 */
public class ValidationConstants {
    public static final String DATE_FORMAT_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z";
    public static final String PHONE_FORMAT_REGEX = "\\d{10}";
    public static final String DATE_FORMAT_MESSAGE = "Date should be in yyyy-MM-dd'T'HH:mm:ss.SSS'Z' format";
    public static final String START_DATE_NOT_NULL_MESSAGE = "Start date cannot be null";
    public static final String END_DATE_NOT_NULL_MESSAGE = "End date cannot be null";
    public static final String BEDS_POSITIVE_MESSAGE = "Number of beds must be more than zero";
    public static final String BEDS_NOT_NULL_MESSAGE = "Beds cannot be null";
    public static final String ROOMS_POSITIVE_MESSAGE = "Number of rooms must be more than zero";
    public static final String ROOMS_NOT_NULL_MESSAGE = "Rooms cannot be null";
    public static final String PROPERTY_ID_POSITIVE_MESSAGE = "Property Id must be positive";
    public static final String PROPERTY_ID_NOT_NULL_MESSAGE = "Property ID cannot be null";
    public static final String TOTAL_GUESTS_POSITIVE_MESSAGE = "Total guests must be more than zero";
    public static final String TOTAL_GUESTS_NOT_NULL_MESSAGE = "Total guests cannot be null";
    public static final String INVALID_PROMO = "The Promo Code is invalid";
    public static final String NIGHTLY_RATE_NOT_NULL = "Nightly rate must be provided";
    public static final String NIGHTLY_RATE_POSITIVE_OR_ZERO = "Nightly rate must be a positive number or zero";
    public static final String SUBTOTAL_NOT_NULL = "Subtotal must be provided";
    public static final String SUBTOTAL_POSITIVE_OR_ZERO = "Subtotal must be a positive number or zero";
    public static final String TAXES_NOT_NULL = "Taxes must be provided";
    public static final String TAXES_POSITIVE_OR_ZERO = "Taxes must be a positive number or zero";
    public static final String VAT_NOT_NULL = "VAT must be provided";
    public static final String VAT_POSITIVE_OR_ZERO = "VAT must be a positive number or zero";
    public static final String TOTAL_NOT_NULL = "Total must be provided";
    public static final String TOTAL_POSITIVE_OR_ZERO = "Total must be a positive number or zero";
    public static final String FIRST_NAME_NOT_BLANK = "First name must not be blank";
    public static final String FIRST_NAME_SIZE = "First name must have at least 2 characters";

    public static final String LAST_NAME_NOT_BLANK = "Last name must not be blank";
    public static final String LAST_NAME_SIZE = "Last name must have at least 2 characters";

    public static final String MAILING_ADDRESS1_NOT_BLANK = "Mailing address 1 must not be blank";
    public static final String MAILING_ADDRESS1_SIZE = "Mailing address 1 must have at least 2 characters";

    public static final String COUNTRY_NOT_BLANK = "Country must not be blank";
    public static final String COUNTRY_SIZE = "Country must have at least 2 characters";

    public static final String CITY_NOT_BLANK = "City must not be blank";
    public static final String CITY_SIZE = "City must have at least 2 characters";

    public static final String STATE_NOT_BLANK = "State must not be blank";
    public static final String STATE_SIZE = "State must have at least 2 characters";

    public static final String ZIPCODE_MIN = "Zipcode must be at least 10000";

    public static final String PHONE_NOT_BLANK = "Phone number must not be blank";
    public static final String PHONE_PATTERN = "Invalid phone number format";

    public static final String EMAIL_NOT_BLANK = "Email must not be blank";
    public static final String EMAIL_FORMAT = "Invalid email format";

    public static final String ADULT_COUNT_POSITIVE = "Adult count must be a positive number";
    public static final String ADULT_COUNT_NOT_NULL = "Adult count must not be null";

    public static final String CHILD_COUNT_NOT_NULL = "Child count must not be null";

    public static final String TOTAL_COST_NOT_NULL = "Total cost must not be null";

    public static final String AMOUNT_DUE_RESORT_NOT_NULL = "Amount due resort must not be null";

    public static final String GUEST_NAME_NOT_BLANK = "Guest name must not be blank";
    public static final String GUEST_NAME_SIZE = "Guest name must have at least 2 characters";

    public static final String ROOM_TYPE_ID_POSITIVE = "Room type ID must be a positive number";
    public static final String ROOM_TYPE_ID_NOT_NULL = "Room type ID must not be null";

    public static final String ROOMS_POSITIVE = "Rooms must be a positive number";
    public static final String ROOMS_NOT_NULL = "Rooms must not be null";

    public static final String PROMOTION_ID_POSITIVE = "Promotion ID must be a positive number";

    public static final String PROMO_CODE_ID_POSITIVE = "Promo code ID must be a positive number";
}
