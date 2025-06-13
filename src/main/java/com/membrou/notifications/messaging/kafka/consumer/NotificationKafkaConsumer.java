package com.membrou.notifications.messaging.kafka.consumer;

import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.notification.dto.NotificationDto;
import com.membrou.notifications.notification.sender.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationKafkaConsumer {

    @Autowired
    private MailSender mailSender;

    @KafkaListener(topics = "notifications", groupId = "notification-sender")
    public void consume(NotificationEvent event) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(event.getMessage());
        notificationDto.setDestination(event.getDestination());
        notificationDto.setType(event.getType());
        notificationDto.setType(event.getType());
        mailSender.send(notificationDto);
    }
}
