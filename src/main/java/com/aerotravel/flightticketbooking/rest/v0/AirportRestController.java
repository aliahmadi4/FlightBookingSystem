package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.services.AirportService;
import com.aerotravel.flightticketbooking.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v0/airports")
public class AirportRestController extends AbstractRestController<Airport> {

    private final AirportService airportService;

    @Autowired
    public AirportRestController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Override
    protected EntityService<Airport> getService() {
        return airportService;
    }
}
