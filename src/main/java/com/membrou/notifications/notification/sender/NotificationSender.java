package com.membrou.notifications.notification.sender;

import com.membrou.notifications.dto.NotificationDto;

public interface NotificationSender {
    void send(NotificationDto notificationDto);
    String getType();
    boolean validateMessage(NotificationDto notificationDto);
}
