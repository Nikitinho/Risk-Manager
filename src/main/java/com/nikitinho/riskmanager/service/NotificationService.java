package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.Risk;
import com.nikitinho.riskmanager.dto.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

@Service
public class NotificationService {

    @Autowired
    private Environment env;

    public void sendNotification(Risk risk, EventType eventType) {

        StringBuilder subject = new StringBuilder(risk.getText());
        String documentStatus;
        switch (eventType) {
            case CREATE:
                documentStatus = "was just created!";
                break;
            case UPDATE:
                documentStatus = "was just updated!";
                break;
            case REMOVE:
                documentStatus = "was just removed!";
                break;
            default:
                return;
        }
        subject.append(" - ");
        subject.append(documentStatus);


        String result = "";
        File file;
        try {
            file = ResourceUtils.getFile("classpath:templates/email.html");
            System.out.print(file.exists());
            result = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = result.replace("[HEADER_PLACEHOLDER]", risk.getText());
        result = result.replace("[SUBHEADER_PLACEHOLDER]", "Risk " + documentStatus);
        result = result.replace("[PARAGRAPH_PLACEHOLDER]", risk.getDescription());
        // temporary solution for absolute path in email
        String absolutePath = "http://localhost:9000/projects/" +
                risk.getProject().getId() + "/viewRisk/" + risk.getId();
        result = result.replace("[BUTTON_LINK_PLACEHOLDER1]", absolutePath);
        result = result.replace("[BUTTON_LINK_PLACEHOLDER2]", absolutePath);


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(env.getProperty("spring.mail.username"),
                                env.getProperty("spring.mail.password"));
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
            message.setSubject(subject.toString());
            message.setContent(result, "text/html; charset=UTF-8");

            risk.getResponsible().forEach(user -> {
                try {
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(user.getEmail()));
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
