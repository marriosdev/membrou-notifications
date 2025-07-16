package com.membrou.notifications.messaging.kafka.consumer;

import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.model.Notification;
import com.membrou.notifications.dto.NotificationDto;
import com.membrou.notifications.repository.NotificationRepository;
import com.membrou.notifications.service.NotificationService;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationKafkaConsumer {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "notifications", groupId = "notification-sender")
    public void consume(NotificationEvent event) {
        try {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setMessage(event.getMessage());
            notificationDto.setDestination(event.getDestination());
            notificationDto.setType(event.getType());
            notificationDto.setType(event.getType());

            notificationService.send(notificationDto);

            Notification notification = this.notificationRepository.findById(
                    event.getMessageId()
            ).orElseThrow(() -> new RuntimeException());

            notification.setStatus("SUCCESS");

            notificationRepository.save(notification);
        } catch (Exception e) {
            Notification notification = this.notificationRepository.findById(
                    event.getMessageId()
            ).orElseThrow(() -> new RuntimeException());

            notification.setStatus("ERROR");
            notificationRepository.save(notification);
            Sentry.captureException(e);
            throw new RuntimeException(e);
        }
    }
}
