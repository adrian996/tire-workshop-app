package com.adrian.london.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;
    private final int statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd:MM:yyyy hh:mm:ss")
    private LocalDateTime timeStamp = LocalDateTime.now();
}
