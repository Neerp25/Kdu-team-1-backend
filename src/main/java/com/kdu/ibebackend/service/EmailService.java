package com.kdu.ibebackend.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AmazonSimpleEmailService amazonSESClient;

    public EmailService(AmazonSimpleEmailService awsEmailService) {
        this.amazonSESClient = awsEmailService;
    }

    public void sendEmail(String to, String subject, String body) {
        Destination destination = new Destination().withToAddresses(to);
        Content subjectContent = new Content().withData(subject);
        Content bodyContent = new Content().withData(body);
        Body emailBody = new Body().withHtml(bodyContent);
        Message message = new Message().withSubject(subjectContent).withBody(emailBody);
        SendEmailRequest request = new SendEmailRequest().withDestination(destination)
                .withMessage(message)
                .withSource("asish.mahapatra@kickdrumtech.com");

        SendEmailResult result = amazonSESClient.sendEmail(request);
    }
}