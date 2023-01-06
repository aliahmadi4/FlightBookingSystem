package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.FlightDto;
import com.aerotravel.flightticketbooking.model.dto.PassengerDto;
import com.aerotravel.flightticketbooking.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0/flights")
@Tag(name = "Flight", description = "Flight resource. Flight booking is possible as well!")
@Slf4j
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
    protected Class<Flight> getEntityClass() {
        return Flight.class;
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
    @Operation(summary = "Attempt to get a flight by its number.")
    public List<FlightDto> findByFlightNumber(@PathVariable String flightNumber) {
        log.info("Searching for flights by number={}", flightNumber);
        return flightService.getAllByByFlightNumber(flightNumber)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/search",
            params = {"departureAirportCode", "destinationAirportCode", "departureDate"})
    @Operation(summary = "Search for flights by departure/destination airport codes and departure date.")
    public List<FlightDto> findByAirportAndDepartureTime(
            @RequestParam("departureAirportCode") String departureAirportCode,
            @RequestParam("destinationAirportCode") String destinationAirportCode,
            @RequestParam("departureDate(yyyy-MM-dd)") String departureDate) {
        log.info("Searching for flights from {} to {} on {}.", departureDate, destinationAirportCode, departureDate);
        var depAirport = airportService.getByCode(departureAirportCode);
        var destAirport = airportService.getByCode(destinationAirportCode);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptDate = LocalDate.parse(departureDate, dtf);

        return flightService.getAllByAirportAndDepartureTime(depAirport, destAirport, deptDate)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/book/{flightId}")
    @Operation(summary = "Attempt to book a ticket for the flight.")
    public PassengerDto bookFlight(@RequestBody Passenger passenger,
                                   @PathVariable("flightId") long flightId) {
        log.info("Booking for the flight {}.", flightId);
        var flight = flightService.getById(flightId);
        passenger.setFlight(flight);
        var savedPassenger = passengerService.save(passenger);

        return toPassengerDto(savedPassenger);
    }

    @GetMapping("/book/verify")
    @Operation(summary = "Attempt to verify booking by flightId and passengerId.")
    public PassengerDto verifyBooking(@RequestParam("passengerId") long passengerId,
                                      @RequestParam("flightId") long flightId) {
        log.info("Verifying booking for passenger {} and flight {}.", passengerId, flightId);
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
    @Operation(summary = "Attempt to cancel a booking.")
    public String cancelBooking(@PathVariable("passengerId") long passengerId) {
        log.info("Canceling booking for {}", passengerId);
        passengerService.deleteById(passengerId);
        return "Something was canceled.";
    }

    private PassengerDto toPassengerDto(Passenger entity) {
        return modelMapper.map(entity, PassengerDto.class);
    }

}
