package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Aircraft;

import java.util.List;

public interface AircraftService extends EntityService<Aircraft>{
   List<Aircraft> getByModel(String modelName);
    List<Aircraft> getByManufacturer(String manufacturerName);

    default EntityNotFoundException buildEntityNotFoundException(long id) {
        return buildEntityNotFoundException("Aircraft", id);
    }
}
