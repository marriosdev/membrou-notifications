package com.membrou.notifications.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter @Setter
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String type;
    private String destination;
    private String subject;
    private String message;
    private LocalDateTime createdAt;
    private String status;
}
