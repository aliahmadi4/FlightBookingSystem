package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.FlightDto;
import com.aerotravel.flightticketbooking.model.dto.PassengerDto;
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

    @GetMapping(value = "/search",
            params = {"departureAirportCode", "destinationAirportCode", "departureTime"})
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

    @PostMapping("/book/{flightId}")
    public PassengerDto bookFlight(@RequestBody Passenger passenger,
                                   @PathVariable("flightId") long flightId) {
        var flight = flightService.getById(flightId);
        passenger.setFlight(flight);
        var savedPassenger = passengerService.save(passenger);

        return toPassengerDto(savedPassenger);
    }

    @GetMapping("/book/verify")
    public PassengerDto verifyBooking(@RequestParam("passengerId") long passengerId,
                                      @RequestParam("flightId") long flightId) {
        var flight = flightService.getById(flightId);
        var foundPassenger = flight.getPassengers()
                .stream()
                .filter(p -> p.getPassengerId() == passengerId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No passenger %s assigned to the flight %s was found", passengerId, flightId)));

        return toPassengerDto(foundPassenger);
    }

    @DeleteMapping("/book/cancel/{passengerId}")
    public String cancelBooking(@PathVariable("passengerId") long passengerId) {
        passengerService.deleteById(passengerId);
        return "Something was canceled.";
    }

    private PassengerDto toPassengerDto(Passenger entity) {
        return modelMapper.map(entity, PassengerDto.class);
    }

}
