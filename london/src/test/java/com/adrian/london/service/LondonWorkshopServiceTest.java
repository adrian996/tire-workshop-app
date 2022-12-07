package com.adrian.london.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adrian.london.client.LondonWorkshopClient;
import com.adrian.london.model.TireChangeBookingRequest;
import com.adrian.london.model.TireChangeTimeBookingResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class LondonWorkshopServiceTest {

    @InjectMocks
    private LondonWorkshopService londonWorkshopServiceTest;
    @Mock
    private LondonWorkshopClient londonWorkshopClient;

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldReturnAllTireChangeTimes() {
        when(londonWorkshopClient.getListOfAvailableTireChangeTimes(any(), any())).thenReturn(tireChangeTimeBookingResponses());
        ResponseEntity<List<TireChangeTimeBookingResponse>> responses = londonWorkshopServiceTest.getAllTireChangeTimes(any(), any());

        assertNotNull(responses);
        assertEquals(HttpStatus.valueOf(200), responses.getStatusCode());
        assertEquals(2, responses.getBody().size());
    }

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldPostSuccesfulTireChangeRequest(){
        when(londonWorkshopClient.bookTireChangeTime(anyString(), any()))
            .thenReturn(tireChangeTimeBookingResponse());

        ResponseEntity<TireChangeTimeBookingResponse> response = londonWorkshopServiceTest.bookTireChangeTime("1111", new TireChangeBookingRequest("Frank"));
        assertNotNull(response);
        assertEquals("1111", response.getBody().getUuid());
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
    }


    private ResponseEntity<List<TireChangeTimeBookingResponse>> tireChangeTimeBookingResponses(){
        return new ResponseEntity<>(List.of(new TireChangeTimeBookingResponse("1111", "2010-01-01"),
                new TireChangeTimeBookingResponse("2222", "2011-01-02")), HttpStatus.OK);
    }

    private ResponseEntity<TireChangeTimeBookingResponse> tireChangeTimeBookingResponse(){
            return new ResponseEntity<>(new TireChangeTimeBookingResponse("1111", "2011-01-02"), HttpStatus.OK);
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
