package com.adrian.london.service;

import com.adrian.london.client.LondonWorkshopClient;
import com.adrian.london.model.TireChangeBookingRequest;
import com.adrian.london.model.TireChangeTimeBookingResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LondonWorkshopService {

    private final LondonWorkshopClient londonWorkshopClient;
    
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getAllTireChangeTimes(LocalDate from, LocalDate until){
        return londonWorkshopClient.getListOfAvailableTireChangeTimes(from, until);
    }

    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(String uuid, TireChangeBookingRequest request){
        return londonWorkshopClient.bookTireChangeTime(uuid, request);
    }
}
