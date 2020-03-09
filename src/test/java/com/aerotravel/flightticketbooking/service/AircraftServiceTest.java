package com.aerotravel.flightticketbooking.service;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.repository.AircraftRepository;
import com.aerotravel.flightticketbooking.services.servicesimpl.AircraftServiceImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AircraftServiceTest {

    @Autowired
    private AircraftServiceImpl aircraftService;

    @MockBean
    private AircraftRepository aircraftRepository;

    //test if the saveAircraft() method.
    @Test
    public void saveAircraftTest(){
        Aircraft aircraft = new Aircraft("Boing", "737", 700);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        assertEquals(aircraft, aircraftService.saveAircraft(aircraft));
        aircraftService.saveAircraft(aircraft);
    }

    //test if the aircraftRepository.save() is called
    @Test
    public void isCalled(){
        Aircraft aircraft = new Aircraft("Boing", "737", 700);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        aircraftService.saveAircraft(aircraft);
        verify(aircraftRepository, times(1)).save(aircraft);
    }


}
