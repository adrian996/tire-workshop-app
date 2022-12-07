package com.adrian.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import com.adrian.model.TireChangeTimeBookingResponse;
import com.adrian.service.ManchesterWorkshopService;


@WebMvcTest(ManchesterWorkshopController.class)
public class ManchesterWorkshopControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ManchesterWorkshopService manchesterWorkshopServiceTest;

    @BeforeEach
    private void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldGetListOfAvailableTireChangeTimes() {
        when(manchesterWorkshopServiceTest.getAllTireChangeTimes(null, null, null))
            .thenReturn(tireChangeTimeBookingResponses());

        RestAssuredMockMvc
            .when()
                .get("api/v1/manchester/times")
            .then()
                .statusCode(200)
                .body("$.size()", Matchers.equalTo(2));

        verify(manchesterWorkshopServiceTest, times(1)).getAllTireChangeTimes(null, null, null);
    }

    @Test
    void shouldBookTireChangeTime() {
        when(manchesterWorkshopServiceTest.bookTireChangeTime(anyInt(), any()))
            .thenReturn(tireChangeTimeBookingResponse());

        RestAssuredMockMvc
            .given()
                .contentType("application/json")
                .body("{\"contactInformation\": \"client\"}")
            .when()
                .post("/api/v1/manchester/1/booking")
            .then()
                .statusCode(200)
                .body("available", Matchers.equalTo(false))  
                .body("id", Matchers.equalTo(1));    
        verify(manchesterWorkshopServiceTest, times(1)).bookTireChangeTime(anyInt(), Mockito.any());
    }

    private ResponseEntity<List<TireChangeTimeBookingResponse>> tireChangeTimeBookingResponses(){
        return new ResponseEntity<>(List.of(new TireChangeTimeBookingResponse(1, "2010-01-01", true),
                new TireChangeTimeBookingResponse(2, "2011-01-02", true)), HttpStatus.OK);
    }

    private ResponseEntity<TireChangeTimeBookingResponse> tireChangeTimeBookingResponse(){
            return new ResponseEntity<>(new TireChangeTimeBookingResponse(1, "2011-01-02", false), HttpStatus.OK);
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
