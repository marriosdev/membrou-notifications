package com.membrou.notifications.notification.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationSenderStrategy {

    private final Map<String, NotificationSender> senderMap;

    @Autowired
    public NotificationSenderStrategy(List<NotificationSender> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(NotificationSender::getType, Function.identity()));
    }

    public NotificationSender getSender(String type) {
        NotificationSender sender = senderMap.get(type.toLowerCase());
        if (sender == null) {
            throw new IllegalArgumentException("Tipo de notificação não suportado: " + type);
        }
        return sender;
    }
}
