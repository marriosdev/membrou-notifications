package com.membrou.notifications.notification.sender.mail;

import com.membrou.notifications.notification.dto.NotificationDto;
import com.membrou.notifications.notification.sender.NotificationSender;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailSender implements NotificationSender {
    private NotificationDto notification;

    @Autowired
    private MailProperties mailProperties;

    public void send(NotificationDto notificationDto) {
        String to = notificationDto.getDestination();
        String from = this.mailProperties.getUsername();

        final String username = this.mailProperties.getUsername();
        final String password = this.mailProperties.getPassword();

        Properties props = new Properties();
        props.put("mail.smtp.auth", this.mailProperties.getAuth());
        props.put("mail.smtp.starttls.enable", this.mailProperties.isStarttls());
        props.put("mail.smtp.host", this.mailProperties.getHost());
        props.put("mail.smtp.port", this.mailProperties.getPort());

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(notificationDto.getSubject());
            message.setText(notificationDto.getMessage());

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
