package com.membrou.notifications.messaging.kafka.consumer;

import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.model.Notification;
import com.membrou.notifications.notification.dto.NotificationDto;
import com.membrou.notifications.repository.NotificationRepository;
import com.membrou.notifications.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
            throw new RuntimeException(e);
        }
    }
}
