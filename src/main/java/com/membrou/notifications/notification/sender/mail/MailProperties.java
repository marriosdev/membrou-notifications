package com.membrou.notifications.notification.sender.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail.smtp")
public class MailProperties {
    private String auth;
    private boolean starttls;
    private String username;
    private String password;
    private String host;
    private String port;
}
