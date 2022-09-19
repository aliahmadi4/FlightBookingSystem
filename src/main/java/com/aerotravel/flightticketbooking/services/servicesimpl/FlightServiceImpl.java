package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.repository.FlightRepository;
import com.aerotravel.flightticketbooking.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceImpl extends AbstractEntityServiceImpl<Flight> implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return flightRepository;
    }

    @Override
    protected String[] getSortByProperties() {
        return new String[]{"departureAirport"};
    }

    @Override
    public List<Flight> getAllByAirportAndDepartureTime(Airport depAirport, Airport destAirport, LocalDate depDate) {
        return flightRepository.findAllByDepartureAirportEqualsAndDestinationAirportEqualsAndDepartureDateEquals(depAirport, destAirport, depDate);
    }

    @Override
    public List<Flight> getAllByByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }
}
