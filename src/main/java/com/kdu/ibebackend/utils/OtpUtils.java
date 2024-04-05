package com.kdu.ibebackend.utils;

import java.util.Random;

public class OtpUtils {
    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 5;

    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }

        return otp.toString();
    }

    public static String otpEmailTemplateGenerator(String otp) {
        return "{ \"otp\": \"" + otp + "\" }";
    }
}
