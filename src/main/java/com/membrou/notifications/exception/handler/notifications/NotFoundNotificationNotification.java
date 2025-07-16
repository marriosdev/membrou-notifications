package com.membrou.notifications.exception.handler.notifications;

public class NotFoundNotificationNotification extends RuntimeException {
    public NotFoundNotificationNotification(String message) {
        super(message);
    }
}