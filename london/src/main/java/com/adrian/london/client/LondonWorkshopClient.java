package com.adrian.london.client;

import com.adrian.london.model.TireChangeBookingRequest;
import com.adrian.london.model.TireChangeTimeBookingResponse;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@FeignClient(name = "london", url = "localhost:9003/api/v1/tire-change-times")
public interface LondonWorkshopClient {
    

        //@DateTimeFormat(pattern="yyyy-MM-dd") 
    @Headers("Content-Type: application/xml")
    @RequestLine("GET /available?from={from}&until={until}")
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getListOfAvailableTireChangeTimes
            (@Param("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from, 
            @Param("until") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate until);

    @Headers("Content-Type: application/xml")
    @RequestLine("PUT /{uuid}/booking")
    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(
            @Param("uuid") String uuid, 
            @RequestBody TireChangeBookingRequest request);
}

