package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.model.Flight;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FlightService {
    public abstract Page<Flight> getAllFlightsPaged(int pageNum);
    public abstract List<Flight> getAllFlights();
    public abstract Flight getFlightById(Long flightId);
    public abstract Flight saveFlight(Flight flight);
    public abstract void deleteFlightById(Long flightId);
}
