package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.aerotravel.flightticketbooking.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0/aircrafts")
public class AircraftRestController extends AbstractRestController<Aircraft> {
    private final AircraftService aircraftService;

    @Autowired
    public AircraftRestController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @Override
    protected EntityService<Aircraft> getService() {
        return aircraftService;
    }

    @GetMapping("/model/{modelName}")
    public List<Aircraft> findByModel(@PathVariable String modelName) {
        return aircraftService.getByModel(modelName);
    }

    @GetMapping("/manufacturer/{manufacturerName}")
    public List<Aircraft> findByManufacturer(@PathVariable String manufacturerName) {
        return aircraftService.getByManufacturer(manufacturerName);
    }
}
