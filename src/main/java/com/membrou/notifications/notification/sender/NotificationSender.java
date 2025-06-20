package com.membrou.notifications.notification.sender;

import com.membrou.notifications.notification.dto.NotificationDto;

public interface NotificationSender {
    void send(NotificationDto notificationDto);
    String getType();

}
