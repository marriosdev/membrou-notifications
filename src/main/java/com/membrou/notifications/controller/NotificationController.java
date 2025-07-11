package com.membrou.notifications.controller;

import com.membrou.notifications.model.Notification;
import com.membrou.notifications.dto.NotificationDto;
import com.membrou.notifications.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("send")
    public ResponseEntity<Notification> sendNotification(@Valid  @RequestBody NotificationDto notificationDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.publish(notificationDto));
    }
}
