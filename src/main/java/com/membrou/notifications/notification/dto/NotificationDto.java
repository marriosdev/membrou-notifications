package com.membrou.notifications.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String type;
    private String destination;
    private String subject;
    private String message;
    private String status;
}