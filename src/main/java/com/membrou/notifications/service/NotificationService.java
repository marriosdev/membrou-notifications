package com.membrou.notifications.service;

import com.membrou.notifications.messaging.kafka.dto.NotificationEvent;
import com.membrou.notifications.messaging.kafka.producer.NotificationKafkaProducer;
import com.membrou.notifications.model.Notification;
import com.membrou.notifications.notification.dto.NotificationDto;
import com.membrou.notifications.notification.sender.mail.MailSender;
import com.membrou.notifications.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private NotificationKafkaProducer notificationKafkaProducer;

    public Notification send(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setSubject(dto.getSubject());
        notification.setType(dto.getType());
        notification.setStatus(dto.getStatus());
        notification.setDestination(dto.getDestination());

        Notification notificationSaved = notificationRepository.save(notification);

        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setType(notificationSaved.getType());
        notificationEvent.setDestination(notificationSaved.getDestination());
        notificationEvent.setSubject(notificationSaved.getSubject());
        notificationEvent.setMessage(notificationSaved.getMessage());
        notificationEvent.setNotificationRecord(notification);

        notificationKafkaProducer.send(notificationEvent);
        return notification;
    }
}
