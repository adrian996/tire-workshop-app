package com.adrian.client;

import com.adrian.model.TireChangeBookingRequest;
import com.adrian.model.TireChangeTimeBookingResponse;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "manchester", url = "localhost:9004/api/v2/tire-change-times")
public interface ManchesterWorkshopClient {
    
    @Headers("Content-Type: application/json")
    @RequestLine("GET ?amount={amount}&page={page}&from={from}")
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getListOfAvailableTireChangeTimes
            (@Param("amount") Integer amount,
             @Param("page") Integer page,
             @Param("from") String from);

    // @Headers("Content-Type: application/json")
    @RequestLine("POST /{id}/booking")
    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(
        @Param("id") Integer id, 
        @RequestBody TireChangeBookingRequest request);
}
