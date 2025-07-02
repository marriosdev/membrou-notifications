package com.membrou.notifications.service;

import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.messaging.kafka.producer.NotificationKafkaProducer;
import com.membrou.notifications.model.Notification;
import com.membrou.notifications.dto.NotificationDto;
import com.membrou.notifications.notification.sender.NotificationSenderStrategy;
import com.membrou.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationKafkaProducer notificationKafkaProducer;

    @Autowired
    private NotificationSenderStrategy notificationSenderStrategy;

    public Notification publish(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setSubject(dto.getSubject());
        notification.setType(dto.getType());
        notification.setStatus("PENDING");
        notification.setDestination(dto.getDestination());
        notification.setCreatedAt(LocalDateTime.now());

        Notification notificationSaved = notificationRepository.save(notification);

        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setType(notificationSaved.getType());
        notificationEvent.setDestination(notificationSaved.getDestination());
        notificationEvent.setSubject(notificationSaved.getSubject());
        notificationEvent.setMessage(notificationSaved.getMessage());
        notificationEvent.setMessageId(notification.getId());

        notificationKafkaProducer.send(notificationEvent);
        return notification;
    }

    public boolean send(NotificationDto notificationDto) {
        this.notificationSenderStrategy.getSender(notificationDto.getType()).send(notificationDto);
        return true;
    }
}
