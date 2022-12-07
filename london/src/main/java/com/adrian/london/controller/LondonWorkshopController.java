package com.adrian.london.controller;

import com.adrian.london.model.TireChangeBookingRequest;
import com.adrian.london.model.TireChangeTimeBookingResponse;
import com.adrian.london.service.LondonWorkshopService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/london")
@CrossOrigin(origins = "*")
//@RequestMapping("LONDON")
public class LondonWorkshopController {

    private final LondonWorkshopService londonWorkshopService;

    @GetMapping("/times")
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getListOfAvailableTireChangeTimes
            (@RequestParam(defaultValue = "2006-01-02") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate from,
            @RequestParam(defaultValue = "2030-01-02") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate until) {
        return londonWorkshopService.getAllTireChangeTimes(from, until);
    }

    @PutMapping("/{uuid}/booking")
    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(
            @PathVariable(name = "uuid") String uuid, 
            @RequestBody TireChangeBookingRequest request) {
        return londonWorkshopService.bookTireChangeTime(uuid, request);
    }

}
