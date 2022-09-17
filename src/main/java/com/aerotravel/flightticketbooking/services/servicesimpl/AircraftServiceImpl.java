package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.repository.AircraftRepository;
import com.aerotravel.flightticketbooking.services.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftServiceImpl extends AbstractEntityServiceImpl<Aircraft> implements AircraftService {
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository){
        this.aircraftRepository = aircraftRepository;
    }

    @Override
    protected JpaRepository<Aircraft, Long> getRepository() {
        return aircraftRepository;
    }

    @Override
    protected String[] getSortByProperties() {
        return new String[]{"model"};
    }

    @Override
    public List<Aircraft> getByModel(String modelName) {
        return aircraftRepository.findByModel(modelName);
    }

    @Override
    public List<Aircraft> getByManufacturer(String manufacturerName) {
        return aircraftRepository.findByManufacturer(manufacturerName);
    }
}
