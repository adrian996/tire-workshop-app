package com.adrian.london.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.adrian.london.model.TireChangeTimeBookingResponse;
import com.adrian.london.service.LondonWorkshopService;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

//import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.RestAssured.*;

@WebMvcTest(LondonWorkshopController.class)
public class LondonWorkshopControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LondonWorkshopService londonWorkshopServiceTest;

    @BeforeEach
    private void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldGetListOfAvailableTireChangeTimes() {
        when(londonWorkshopServiceTest.getAllTireChangeTimes(any(), any()))
            .thenReturn((new ResponseEntity<>(List.of(tireChangeTimeBookingResponse(), tireChangeTimeBookingResponse()), HttpStatus.OK)));

        RestAssuredMockMvc
            .given()
                .params("from", "2006-01-01")
                .param("until", "2020-01-01")
            .when()
                .get("api/v1/london/times")
            .then()
                .statusCode(200)
                .body("$.size()", Matchers.equalTo(2));

        verify(londonWorkshopServiceTest, times(1)).getAllTireChangeTimes(any(), any());
    }

    @Disabled
    @Test
    @MethodSource("tireChangeTimeBookingResponses")
    void shouldBookTireChangeTime() {
        when(londonWorkshopServiceTest.bookTireChangeTime(anyString(), any()))
            .thenReturn(new ResponseEntity<>(tireChangeTimeBookingResponse(), HttpStatus.OK));

        RestAssuredMockMvc
            .given()
                //.body("{ \"contactInformation\" : \"client\"}")
            .when()
                .put("/api/v1/london/{uuid}/booking", "1111")
            .then()
                //.contentType(ContentType.XML)
                //.statusCode(200)
                .body("available", Matchers.equalTo(false))  
                .body("id", Matchers.equalTo(1));    
            verify(londonWorkshopServiceTest, times(1)).bookTireChangeTime(anyString(), Mockito.any());
        }

    private TireChangeTimeBookingResponse tireChangeTimeBookingResponse(){
            return new TireChangeTimeBookingResponse("1111", "2011-01-02");
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
