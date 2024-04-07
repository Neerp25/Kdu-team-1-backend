package com.kdu.ibebackend.utils;

import com.kdu.ibebackend.dto.response.BookingResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "+/XCeC1ZuIPlgCHcM2RY1qCEh4Xw09BjpLEiVhUQdYU=";
    private static final long EXPIRATION_TIME = 300000;
//    private static final long EXPIRATION_TIME = 180000;

    private static SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateEmailToken(String email, String roomTypeId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder().subject(email)
                .claim("roomTypeId", roomTypeId).claim("email", email).issuedAt(now).expiration(expiryDate).signWith(getSignKey(), Jwts.SIG.HS256).compact();
    }

    public static String generateBookingToken(String reservationId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("reservationId", reservationId);

        Date now = new Date();

        return Jwts.builder().subject("bookingdata").claims(claims).issuedAt(now).signWith(getSignKey(), Jwts.SIG.HS256).compact();
    }
}
