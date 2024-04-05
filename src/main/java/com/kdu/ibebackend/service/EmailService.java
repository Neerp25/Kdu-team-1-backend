package com.kdu.ibebackend.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AmazonSimpleEmailService amazonSESClient;
    private Environment env;

    public EmailService(AmazonSimpleEmailService awsEmailService, Environment env) {
        this.amazonSESClient = awsEmailService;
        this.env = env;
    }

    public void sendEmail(String to, String subject, String body) {
        Destination destination = new Destination().withToAddresses(to);
        Content subjectContent = new Content().withData(subject);
        Content bodyContent = new Content().withData(body);
        Body emailBody = new Body().withHtml(bodyContent);
        Message message = new Message().withSubject(subjectContent).withBody(emailBody);
        SendEmailRequest request = new SendEmailRequest().withDestination(destination)
                .withMessage(message)
                .withSource(env.getProperty("email.source"));

        SendEmailResult result = amazonSESClient.sendEmail(request);
    }

    public void sendTemplatedEmail(String templateName, String recipientEmail, String templateData) {
        SendTemplatedEmailRequest emailRequest = new SendTemplatedEmailRequest()
                .withDestination(new Destination().withToAddresses(recipientEmail))
                .withSource(env.getProperty("email.source"))
                .withTemplate(templateName)
                .withTemplateData(templateData);

        amazonSESClient.sendTemplatedEmail(emailRequest);
    }
}