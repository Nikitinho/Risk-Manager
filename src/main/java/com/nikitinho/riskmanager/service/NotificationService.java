package com.nikitinho.riskmanager.service;

import com.nikitinho.riskmanager.domain.Risk;
import com.nikitinho.riskmanager.dto.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotification(Risk risk, EventType eventType) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply.rmt.beta@gmail.com");
        StringBuilder subject = new StringBuilder(risk.getText());
        switch (eventType) {
            case CREATE:
                subject.append(" - was just created!");
                break;
            case UPDATE:
                subject.append(" - was just updated!");
                break;
            case REMOVE:
                subject.append(" - was just removed!");
                break;
            default:
                return;
        }
        mail.setSubject(subject.toString());
        mail.setText(risk.getDescription());
        risk.getResponsible().forEach(user -> {
            mail.setTo(user.getEmail());
            javaMailSender.send(mail);
        });
    }
}
