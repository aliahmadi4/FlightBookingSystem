package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.dto.FlightDto;
import com.aerotravel.flightticketbooking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/flights")
public class FlightRestController extends AbstractRestController<Flight, FlightDto> {

    private final FlightService flightService;
    private final AirportService airportService;

    private final AircraftService aircraftService;

    private final PassengerService passengerService;

    @Autowired
    public FlightRestController(FlightService flightService, AirportService airportService, AircraftService aircraftService, PassengerService passengerService) {
        this.flightService = flightService;
        this.airportService = airportService;
        this.aircraftService = aircraftService;
        this.passengerService = passengerService;
    }

    @Override
    protected EntityService<Flight> getService() {
        return flightService;
    }

    @Override
    protected FlightDto convertToDto(Flight entity) {
        return modelMapper.map(entity, FlightDto.class);
    }

    @Override
    protected Flight convertToEntity(FlightDto entityDto) {
        var candidate = modelMapper.map(entityDto, Flight.class);

        candidate.setAircraft(aircraftService.getById(entityDto.getAircraftId()));
        candidate.setPassengers(passengerService.getAllById(entityDto.getPassengerIds()));

        if (null != entityDto.getDepartureAirportCode()) {
            var depAirport = airportService.getByCode(entityDto.getDepartureAirportCode());
            candidate.setDepartureAirport(depAirport);
        }
        if (null != entityDto.getDestinationAirportCode()) {
            var destAirport = airportService.getByCode(entityDto.getDestinationAirportCode());
            candidate.setDestinationAirport(destAirport);
        }

        return candidate;
    }

    @GetMapping("/number/{flightNumber}")
    public List<FlightDto> findByFlightNumber(@PathVariable String flightNumber) {
        return flightService.getAllByByFlightNumber(flightNumber)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"departureAirportCode", "destinationAirportCode", "departureTime"})
    public List<FlightDto> findByAirportAndDepartureTime(
            @RequestParam("departureAirportCode") String departureAirportCode,
            @RequestParam("destinationAirportCode") String destinationAirportCode,
            @RequestParam("departureTime") String departureTime) {

        var depAirport = airportService.getByCode(departureAirportCode);
        var destAirport = airportService.getByCode(destinationAirportCode);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);

        return flightService.getAllByAirportAndDepartureTime(depAirport, destAirport, deptTime)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
