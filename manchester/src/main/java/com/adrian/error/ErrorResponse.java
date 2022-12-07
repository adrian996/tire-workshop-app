package com.adrian.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse extends Exception{
    private final int code;
    private final String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd:MM:yyyy hh:mm:ss")
    private LocalDateTime timeStamp = LocalDateTime.now();
}
