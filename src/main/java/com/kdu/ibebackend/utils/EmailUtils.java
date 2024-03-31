package com.kdu.ibebackend.utils;

import com.kdu.ibebackend.constants.Constants;
import com.kdu.ibebackend.constants.EmailTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtils {
    public static String formLinkGenerator(String email, Integer roomTypeId) {
        String token = JwtUtils.generateToken(email, roomTypeId.toString());
        log.info(token);
        return Constants.FORM_LINK + "?token=" + token;
    }

    public static String htmlLinkInjector(String link) {
        return EmailTemplate.EMAIL_TEMPLATE.formatted(link);
    }
}
