package com.membrou.notifications.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    @NotBlank(message = "type não pode ser vazio")
    private String type;

    @NotBlank(message = "destination não pode ser vazio")
    private String destination;

    @NotBlank(message = "subject não pode ser vazio")
    private String subject;

    @NotBlank(message = "message não pode ser vazio")
    private String message;

    private String status;
}