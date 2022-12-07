package com.adrian.service;

import com.adrian.client.ManchesterWorkshopClient;
import com.adrian.model.TireChangeBookingRequest;
import com.adrian.model.TireChangeTimeBookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@RequiredArgsConstructor
@Service
public class ManchesterWorkshopService {

    private final ManchesterWorkshopClient manchesterWorkshopClient;
    
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getAllTireChangeTimes(Integer amount, Integer page, String from){
        return manchesterWorkshopClient.getListOfAvailableTireChangeTimes(amount, page, from);
    }

    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(int id, TireChangeBookingRequest request){
        return manchesterWorkshopClient.bookTireChangeTime(id, request);
    }
}
