package com.aerotravel.flightticketbooking.service;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.repository.AircraftRepository;
import com.aerotravel.flightticketbooking.services.AircraftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AircraftServiceTest {

    @Autowired
    private AircraftService aircraftService;

    @MockBean
    private AircraftRepository aircraftRepository;

    Aircraft aircraft = new Aircraft("Boing", "737", 700);


    //test if the saveAircraft() method.
    @Test
    public void saveAircraftTest() {
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        assertEquals(aircraft, aircraftService.save(aircraft));
        aircraftService.save(aircraft);
    }

    //saveAircraft should throw exception if the argument passed is null
    @Test
    public void saveAircraftThrowsIllegalArgumentException() {
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> aircraftService.save(null));

        String expectedMessage = "Cannot save null!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //test if the aircraftRepository.save() is called
    @Test
    public void isCalled() {
        Aircraft aircraft = new Aircraft("Boing", "737", 700);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        aircraftService.save(aircraft);
        verify(aircraftRepository, times(1)).save(aircraft);
    }

    @Test
    public void getById_success() {
        long id = 5960;
        var aircraft = new Aircraft(id, "Yak", "242", 160);
        when(aircraftRepository.findById(id)).thenReturn(Optional.of(aircraft));

        var actual = aircraftService.getById(id);

        verify(aircraftRepository, times(1)).findById(id);
        assertEquals(aircraft, actual);
    }

    @Test
    public void getAll_success() {
        List<Aircraft> data = List.of(
                new Aircraft(7375, "Yak", "MS-21", 160),
                new Aircraft(7376, "Yak", "242", 160),
                aircraft
        );
        when(aircraftRepository.findAll()).thenReturn(data);

        var actual = aircraftService.getAll();

        verify(aircraftRepository, times(1)).findAll();
        assertEquals(data, actual);
    }

    @Test
    public void getByModel_success() {
        String model = "Il-96";
        var il96 = new Aircraft(8890, "Ilju6in", model, 250);
        when(aircraftRepository.findByModel(model)).thenReturn(List.of(il96));

        var actual = aircraftService.getByModel(model);

        verify(aircraftRepository, times(1)).findByModel(model);
        assertEquals(1, actual.size());
        assertEquals(il96, actual.get(0));
    }

    @Test
    public void getByManufacturer_success() {
        String manufacturer = "Tupolev";
        var tu214 = new Aircraft(100102, manufacturer, "Tu-214", 170);
        when(aircraftRepository.findByManufacturer(manufacturer)).thenReturn(List.of(tu214));

        var actual = aircraftService.getByManufacturer(manufacturer);

        verify(aircraftRepository, times(1)).findByManufacturer(manufacturer);
        assertEquals(1, actual.size());
        assertEquals(tu214, actual.get(0));
    }

}
