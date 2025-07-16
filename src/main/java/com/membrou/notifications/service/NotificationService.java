package com.membrou.notifications.service;

import com.membrou.notifications.exception.handler.notifications.NotFoundNotificationNotification;
import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.messaging.kafka.producer.NotificationKafkaProducer;
import com.membrou.notifications.model.Notification;
import com.membrou.notifications.dto.NotificationDto;
import com.membrou.notifications.notification.sender.NotificationSender;
import com.membrou.notifications.notification.sender.NotificationSenderStrategy;
import com.membrou.notifications.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    final private NotificationRepository notificationRepository;
    final private NotificationKafkaProducer notificationKafkaProducer;
    final private NotificationSenderStrategy notificationSenderStrategy;

    public Notification publish(NotificationDto dto) {
        NotificationSender sender = this.notificationSenderStrategy.getSender(dto.getType());
        sender.validateMessage(dto);

        Notification notification = Notification.builder()
            .type(dto.getType())
            .subject(dto.getSubject())
            .message(dto.getMessage())
            .destination(dto.getDestination())
            .status("PENDING")
            .createdAt(LocalDateTime.now())
            .build();

        Notification saved = notificationRepository.save(notification);

        NotificationEvent event = new NotificationEvent();
        event.setType(saved.getType());
        event.setSubject(saved.getSubject());
        event.setMessage(saved.getMessage());
        event.setDestination(saved.getDestination());
        event.setMessageId(saved.getId());

        notificationKafkaProducer.send(event);

        return saved;
    }

    public boolean send(NotificationDto notificationDto) {
        this.notificationSenderStrategy.getSender(notificationDto.getType()).send(notificationDto);
        return true;
    }

    public Notification getById(String id) {
        return this.notificationRepository.findById(id).orElseThrow(() -> new NotFoundNotificationNotification("Notificação não encontrada"));
    }
}
