package com.adrian.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.adrian.client.ManchesterWorkshopClient;
import com.adrian.model.TireChangeBookingRequest;
import com.adrian.model.TireChangeTimeBookingResponse;

@ExtendWith(MockitoExtension.class)
public class ManchesterWorkshopServiceTest {

    @InjectMocks
    private ManchesterWorkshopService manchesterWorkshopServiceTest;

    @Mock
    private ManchesterWorkshopClient manchesterWorkshopClient;

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldReturnAllTireChangeTimes() {
        when(manchesterWorkshopClient.getListOfAvailableTireChangeTimes(null, null, null)).thenReturn(tireChangeTimeBookingResponses());
        ResponseEntity<List<TireChangeTimeBookingResponse>> responses = manchesterWorkshopServiceTest.getAllTireChangeTimes(null, null, null);

        assertNotNull(responses);
        assertEquals(HttpStatus.valueOf(200), responses.getStatusCode());
        assertEquals(2, responses.getBody().size());
    }

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldPostSuccesfulTireChangeRequest(){
        lenient().when(manchesterWorkshopClient.bookTireChangeTime(anyInt(), any()))
            .thenReturn(tireChangeTimeBookingResponse());

        ResponseEntity<TireChangeTimeBookingResponse> response = manchesterWorkshopServiceTest.bookTireChangeTime(1, new TireChangeBookingRequest("Frank"));
        assertNotNull(response);
        assertEquals(1, response.getBody().getId());
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
    }


    private ResponseEntity<List<TireChangeTimeBookingResponse>> tireChangeTimeBookingResponses(){
        return new ResponseEntity<>(List.of(new TireChangeTimeBookingResponse(1, "2010-01-01", true),
                new TireChangeTimeBookingResponse(2, "2011-01-02", true)), HttpStatus.OK);
    }

    private ResponseEntity<TireChangeTimeBookingResponse> tireChangeTimeBookingResponse(){
            return new ResponseEntity<>(new TireChangeTimeBookingResponse(1, "2011-01-02", true), HttpStatus.OK);
    }

    public Date StringToDate(String s){
        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result  = dateFormat.parse(s);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        return result;
    }
}
