package com.adrian.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TireChangeTimeBookingResponse {
    private int id;
    private String time;
    private boolean available;
}
