package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Passenger;

public interface PassengerService extends EntityService<Passenger> {
    default EntityNotFoundException buildEntityNotFoundException(long id) {
        return buildEntityNotFoundException("Passenger", id);
    }
}
