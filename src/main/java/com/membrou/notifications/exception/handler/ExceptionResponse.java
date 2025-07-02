package com.membrou.notifications.exception.handler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse implements Serializable{
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private String error;
    private Integer status;
    private String message;
    private List errors;
    private String path;
}
