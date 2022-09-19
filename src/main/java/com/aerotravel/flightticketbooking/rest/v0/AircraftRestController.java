package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.dto.AircraftDto;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.aerotravel.flightticketbooking.services.EntityService;
import com.aerotravel.flightticketbooking.services.FlightService;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/aircrafts")
public class AircraftRestController extends AbstractRestController<Aircraft, AircraftDto> {
    private final AircraftService aircraftService;
    private final FlightService flightService;

    @Autowired
    public AircraftRestController(AircraftService aircraftService, FlightService flightService) {
        this.aircraftService = aircraftService;
        this.flightService = flightService;
    }

    @Override
    protected EntityService<Aircraft> getService() {
        return aircraftService;
    }

    @Override
    protected AircraftDto convertToDto(Aircraft entity) {
        return modelMapper.map(entity, AircraftDto.class);
    }

    @Override
    protected Aircraft convertToEntity(AircraftDto entityDto) {
        var candidate = modelMapper.map(entityDto, Aircraft.class);
        candidate.setFlights(flightService.getAllById(entityDto.getFlightIds()));

        return candidate;
    }

    @GetMapping("/model/{modelName}")
    public List<AircraftDto> findByModel(@PathVariable String modelName) {
        return aircraftService.getByModel(modelName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/manufacturer/{manufacturerName}")
    public List<AircraftDto> findByManufacturer(@PathVariable String manufacturerName) {
        return aircraftService.getByManufacturer(manufacturerName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
