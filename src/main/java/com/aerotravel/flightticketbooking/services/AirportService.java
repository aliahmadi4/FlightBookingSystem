package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Airport;

public interface AirportService extends EntityService<Airport>{

    default EntityNotFoundException buildEntityNotFoundException(long id) {
        return buildEntityNotFoundException("Airport", id);
    }
}
