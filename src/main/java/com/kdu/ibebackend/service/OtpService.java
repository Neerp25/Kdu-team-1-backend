package com.kdu.ibebackend.service;

import com.kdu.ibebackend.constants.Constants;
import com.kdu.ibebackend.constants.EmailTemplate;
import com.kdu.ibebackend.constants.Errors;
import com.kdu.ibebackend.exceptions.custom.OtpException;
import com.kdu.ibebackend.models.dynamodb.OtpEntry;
import com.kdu.ibebackend.repository.DynamoRepository;
import com.kdu.ibebackend.utils.OtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class OtpService {
    private DynamoRepository dynamoRepository;
    private EmailService emailService;

    @Autowired
    OtpService(DynamoRepository dynamoRepository, EmailService emailService) {
        this.dynamoRepository = dynamoRepository;
        this.emailService = emailService;
    }


    public String sendOtp(String email) {
        String otp = OtpUtils.generateOTP();

        OtpEntry otpEntry = new OtpEntry();
        otpEntry.setExpirationTime(Instant.now().getEpochSecond() + 30);
        otpEntry.setUserEmail(email);
        otpEntry.setOtp(Integer.parseInt(otp));

        String templateData = OtpUtils.otpEmailTemplateGenerator(otp);

        log.info(otpEntry.toString());

        dynamoRepository.saveOtpEntry(otpEntry);
        emailService.sendTemplatedEmail(EmailTemplate.OTP_TEMPLATE_NAME, email, templateData);

        return otp;
    }

    public String verifyOtp(String email, String otp) throws OtpException {
        Optional<OtpEntry> res = Optional.ofNullable(dynamoRepository.getOtpEntry(email));

        if(res.isEmpty()) throw new OtpException(Errors.INVALID_OTP_USER);
        if(res.get().getOtp() != Integer.parseInt(otp)) throw new OtpException(Errors.INVALID_OTP);
        if(res.get().getExpirationTime() < Instant.now().getEpochSecond()) throw new OtpException((Errors.EXPIRED_OTP));

        return Constants.OTP_SUCCESS;
    }
}
