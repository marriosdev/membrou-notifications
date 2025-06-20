package com.membrou.notifications.notification.sender.whatsapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "whatsapp.membrou")
public class WhatsappProperties {
    private String host;
    private String token;
}
