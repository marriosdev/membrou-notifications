package com.membrou.notifications.messaging.kafka.dto;

import com.membrou.notifications.model.Notification;
import lombok.Data;

@Data
public class NotificationEvent {
    private String type;
    private String destination;
    private String subject;
    private String message;
    private Notification notificationRecord;
}