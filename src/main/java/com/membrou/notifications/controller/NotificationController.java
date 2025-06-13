package com.membrou.notifications.controller;

import com.membrou.notifications.notification.dto.NotificationDto;
import com.membrou.notifications.notification.sender.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("v1/notifications")
public class NotificationController {
    @Autowired
    private MailSender mailSender;

    @PostMapping("send")
    public ResponseEntity<String> sendNotification()
    {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setType("mail");
        notificationDto.setDestination("mail@marrios.com");
        notificationDto.setSubject("Hello World");
        notificationDto.setMessage("Bom dia meu amigo");
        mailSender.send(new NotificationDto());
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}
