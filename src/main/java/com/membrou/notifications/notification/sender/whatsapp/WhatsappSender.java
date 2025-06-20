package com.membrou.notifications.notification.sender.whatsapp;

import com.membrou.notifications.notification.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class WhatsappSender implements WhatsappSenderContract {

    @Autowired
    private WhatsappProperties whatsappProperties;
    private HttpClient client;

    @Override
    public String getType() {
        return "whatsapp";
    }

    public void send(NotificationDto notificationDto) {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        final String phoneNumber = notificationDto.getDestination();
        final String message = notificationDto.getMessage();

        try {
            String json = String.format("""
                {
                    "number": "%s",
                    "text": "%s"
                }
                """, phoneNumber, message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(whatsappProperties.getHost()))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("apikey", whatsappProperties.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new RuntimeException("Erro ao enviar mensagem: " + response.body());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Falha ao enviar mensagem WhatsApp", e);
        }
    }
}
