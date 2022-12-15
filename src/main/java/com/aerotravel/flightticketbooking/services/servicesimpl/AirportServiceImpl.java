package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.repository.AirportRepository;
import com.aerotravel.flightticketbooking.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl extends AbstractEntityServiceImpl<Airport> implements AirportService {

    private final AirportRepository airportRepository;
    private final String[] sortBy = new String[]{"airportName"};

    @Autowired
    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    protected JpaRepository<Airport, Long> getRepository() {
        return airportRepository;
    }

    @Override
    protected String[] getSortByProperties() {
        return sortBy;
    }

    @Override
    public Airport getByCode(String airportCode) {
        if (null == airportCode) throw new IllegalArgumentException("Airport code shall not be null.");

        return airportRepository.findByAirportCode(airportCode)
                .orElseThrow(() -> new EntityNotFoundException("Could not find airport by code=" + airportCode));
    }
}
