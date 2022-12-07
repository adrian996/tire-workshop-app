package com.adrian.controller;

import com.adrian.model.TireChangeBookingRequest;
import com.adrian.model.TireChangeTimeBookingResponse;
import com.adrian.service.ManchesterWorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/manchester")
@CrossOrigin(origins = "*")
//@RequestMapping("manchester")
public class ManchesterWorkshopController {

    private final ManchesterWorkshopService manchesterWorkshopService;

    @GetMapping("/times")
    public ResponseEntity<List<TireChangeTimeBookingResponse>> getListOfAvailableTireChangeTimes
            (@RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String from) {
        return manchesterWorkshopService.getAllTireChangeTimes(amount, page, from);
    }

    @PostMapping("/{id}/booking")
    public ResponseEntity<TireChangeTimeBookingResponse> bookTireChangeTime(
            @PathVariable(name = "id") int id, 
            @RequestBody TireChangeBookingRequest request) {
        ResponseEntity<TireChangeTimeBookingResponse> response = manchesterWorkshopService.bookTireChangeTime(id, request);
        System.out.println(response);
        return response;
    }
}
