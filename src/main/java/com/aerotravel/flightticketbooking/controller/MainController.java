package com.aerotravel.flightticketbooking.controller;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.aerotravel.flightticketbooking.services.AirportService;
import com.aerotravel.flightticketbooking.services.FlightService;
import com.aerotravel.flightticketbooking.services.PassengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Autowired
    AirportService airportService;
    @Autowired
    AircraftService aircraftService;
    @Autowired
    FlightService flightService;
    @Autowired
    PassengerService passengerService;

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/airport/new")
    public String showAddAirportPage(Model model) {
        log.info("About to open the New Airport page");
        model.addAttribute("airport", new Airport());
        return "newAirport";
    }

    @GetMapping("/airport/edit")
    public String showEditAirport(@PathParam("airportId") long airportId, Model model) {
        log.info("Opening airport={} to edit", airportId);
        var record = airportService.getById(airportId);

        model.addAttribute("airport", record);
        return "editAirport";
    }

    @PostMapping("/airport/edit")
    public String editAirport(@PathParam("airportId") long airportId, @Valid Airport airport,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            airport.setAirportId(airportId);
            log.info("There were errors upon  editing Airport entity");
            return "editAirport";
        }

        log.info("About to update airport={}", airport.getAirportName());
        airportService.save(airport);
        return "redirect:/airports";
    }

    @PostMapping("/airport/new")
    public String saveAirport(@Valid @ModelAttribute("airport") Airport airport, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("airport", new Airport());
            log.info("There were errors upon saving Airport entity");

            return "newAirport";
        }
        log.info("About to save airport={}", airport.getAirportName());
        airportService.save(airport);
        model.addAttribute("airports", airportService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "airports";
    }

    @GetMapping("/airport/delete")
    public String deleteAirport(@PathParam("airportId") long airportId, Model model) {
        log.info("About to delete Airport Id={}", airportId);
        airportService.deleteById(airportId);
        model.addAttribute("airports", airportService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "airports";
    }

    @GetMapping("/airports")
    public String showAirportsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        log.info("About to show airports");
        model.addAttribute("airports", airportService.getAllPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "airports";
    }

    @GetMapping("/aircraft/new")
    public String showAddAircraftPage(Model model) {
        log.info("About to show aircrafts");
        model.addAttribute("aircraft", new Aircraft());
        return "newAircraft";
    }

    @PostMapping("/aircraft/new")
    public String saveAircraft(@Valid @ModelAttribute("aircraft") Aircraft aircraft, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("aircraft", new Aircraft());
            log.info("There were errors upon saving Aircraft entity:\n{}", aircraft);
            return "newAircraft";
        }

        log.info("About to save aircraft={}", aircraft.getModel());
        aircraftService.save(aircraft);
        model.addAttribute("aircrafts", aircraftService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "aircrafts";
    }

    @GetMapping("/aircraft/edit")
    public String showEditAircraft(@PathParam("aircraftId") long aircraftId, Model model) {
        log.info("About to edit aircraft entity, id={}", aircraftId);
        var record = aircraftService.getById(aircraftId);

        model.addAttribute("aircraft", record);
        return "editAircraft";
    }

    @PostMapping("/aircraft/edit")
    public String editAircraft(@PathParam("aircraftId") long aircraftId, @Valid Aircraft aircraft,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            aircraft.setAircraftId(aircraftId);
            log.info("Failed to edit the aircraft, id={}", aircraftId);
            return "editAircraft";
        }

        log.info("About to update aircraft={}", aircraft.getModel());
        aircraftService.save(aircraft);
        return "redirect:/aircrafts";
    }

    @GetMapping("/aircraft/delete")
    public String deleteAircraft(@PathParam("aircraftId") long aircraftId, Model model) {
        log.info("About to delete aircraft, id=={}", aircraftId);
        aircraftService.deleteById(aircraftId);
        model.addAttribute("aircrafts", aircraftService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "aircrafts";
    }

    @GetMapping("/aircrafts")
    public String showAircraftsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        log.info("About to show aircraft list");
        model.addAttribute("aircrafts", aircraftService.getAllPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "aircrafts";
    }

    @GetMapping("/flight/new")
    public String showNewFlightPage(Model model) {
        log.info("Opening the New flight page");
        model.addAttribute("flight", new Flight());
        model.addAttribute("aircrafts", aircraftService.getAll());
        model.addAttribute("airports", airportService.getAll());
        return "newFlight";
    }

    @PostMapping("/flight/new")
    public String saveFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult bindingResult,
                             @RequestParam("departureAirport") long departureAirport,
                             @RequestParam("destinationAirport") long destinationAirport,
                             @RequestParam("aircraft") long aircraftId,
                             @RequestParam("arrivalTime") String arrivalTime,
                             @RequestParam("departureTime") String departureTime,
                             Model model) {

        log.info("About to save flight from {}/{} to {}/{}",
                departureAirport, departureTime, destinationAirport, arrivalTime);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("flight", new Flight());
            model.addAttribute("aircrafts", aircraftService.getAll());
            model.addAttribute("airports", airportService.getAll());

            log.info("Failed to save the flight:\n{}", flight);
            return "newFlight";
        }
        if (departureAirport == destinationAirport) {
            model.addAttribute("sameAirportError", "Departure and destination airport can't be same");
            model.addAttribute("flight", new Flight());
            model.addAttribute("aircrafts", aircraftService.getAll());
            model.addAttribute("airports", airportService.getAll());
            return "newFlight";
        }

        flight.setAircraft(aircraftService.getById(aircraftId));
        flight.setDepartureAirport(airportService.getById(departureAirport));
        flight.setDestinationAirport(airportService.getById(destinationAirport));
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        log.info("About to save the flight:\n{}", flight);
        flightService.save(flight);

        model.addAttribute("flights", flightService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "flights";
    }

    @GetMapping("/flight/delete")
    public String deleteFlight(@PathParam("flightId") long flightId, Model model) {
        log.info("About to delete the flight, id={}", flightId);
        flightService.deleteById(flightId);
        model.addAttribute("flights", flightService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "flights";
    }

    @GetMapping("/flights")
    public String showFlightsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        log.info("About to show the list of flights");
        model.addAttribute("flights", flightService.getAllPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "flights";
    }

    @GetMapping("/flights_list")
    public String showSimpleFlightsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        log.info("About to show the simple list of flights");
        model.addAttribute("flights", flightService.getAllPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "flights_list";
    }

    @GetMapping("/flight/search")
    public String showSearchFlightPage(Model model) {
        log.info("About to show the search flights page");
        model.addAttribute("airports", airportService.getAll());
        model.addAttribute("flights", null);
        return "searchFlight";
    }

    @PostMapping("/flight/search")
    public String searchFlight(@RequestParam("departureAirport") long departureAirport,
                               @RequestParam("destinationAirport") long destinationAirport,
                               @RequestParam("departureTime") String departureTime,
                               Model model) {
        log.info("About to search for a flight from {} to {} on {}", departureAirport, destinationAirport, departureTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);
        Airport depAirport = airportService.getById(departureAirport);
        Airport destAirport = airportService.getById(destinationAirport);

        if (departureAirport == destinationAirport) {
            model.addAttribute("AirportError", "Departure and destination airport cant be same!");
            model.addAttribute("airports", airportService.getAll());
            return "searchFlight";
        }
        List<Flight> flights = flightService.getAllByAirportAndDepartureTime(depAirport, destAirport, deptTime);
        if (flights.isEmpty()) {
            log.info("No flights were found from {} to {} on {}", departureAirport, destinationAirport, departureTime);
            model.addAttribute("notFound", "No Record Found!");
        } else {
            model.addAttribute("flights", flights);
        }

        model.addAttribute("airports", airportService.getAll());
        return "searchFlight";
    }

    @GetMapping("/flight/book")
    public String showBookFlightPage(Model model) {
        log.info("About to show the flight page");
        model.addAttribute("airports", airportService.getAll());
        return "bookFlight";
    }

    @PostMapping("/flight/book")
    public String searchFlightToBook(@RequestParam("departureAirport") long departureAirport,
                                     @RequestParam("destinationAirport") long destinationAirport,
                                     @RequestParam("departureTime") String departureTime,
                                     Model model) {
        log.info("About to book a flight from {} to {} on {}", departureAirport, destinationAirport, departureTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate deptTime = LocalDate.parse(departureTime, dtf);
        Airport depAirport = airportService.getById(departureAirport);
        Airport destAirport = airportService.getById(destinationAirport);

        if (departureAirport == destinationAirport) {
            model.addAttribute("AirportError", "Departure and destination airport cant be same!");
            model.addAttribute("airports", airportService.getAll());
            return "bookFlight";
        }
        List<Flight> flights = flightService.getAllByAirportAndDepartureTime(depAirport, destAirport, deptTime);
        if (flights.isEmpty()) {
            log.info("No flights were found from {} to {} on {}", departureAirport, destinationAirport, departureTime);
            model.addAttribute("notFound", "No Record Found!");
        } else {
            model.addAttribute("flights", flights);
        }
        model.addAttribute("airports", airportService.getAll());
        return "bookFlight";
    }

    @GetMapping("/flight/book/new")
    public String showCustomerInfoPage(@RequestParam long flightId, Model model) {
        log.info("About to show the new passenger page");
        model.addAttribute("flightId", flightId);
        model.addAttribute("passenger", new Passenger());
        return "newPassenger";
    }

    @PostMapping("/flight/book/new")
    public String bookFlight(@Valid @ModelAttribute("passenger") Passenger passenger,
                             BindingResult bindingResult,
                             @RequestParam("flightId") long flightId, Model model) {
        log.info("About to book a ticket for flight {}", flightId);
        Flight flight = flightService.getById(flightId);
        Passenger passenger1 = passenger;
        passenger1.setFlight(flight);
        passengerService.save(passenger1);
        model.addAttribute("passenger", passenger1);
        return "confirmationPage";
    }

    @GetMapping("/flight/book/verify")
    public String showVerifyBookingPage() {
        log.info("Navigating to the page to verify bookings");
        return "verifyBooking";
    }

    @PostMapping("/flight/book/verify")
    public String showVerifyBookingPageResult(@RequestParam("flightId") long flightId,
                                              @RequestParam("passengerId") long passengerId,
                                              Model model) {
        log.info("About to verify booking for flight={} and passenger={}", flightId, passengerId);
        var flightOptional = flightService.getOptionallyById(flightId);
        if (flightOptional.isPresent()) {
            var flight = flightOptional.get();
            model.addAttribute("flight", flight);
            var passengers = flight.getPassengers();
            Passenger passenger = null;
            for (Passenger p : passengers) {
                if (p.getPassengerId() == passengerId) {
                    passenger = passengerService.getById(passengerId);
                    model.addAttribute("passenger", passenger);
                }
            }
            if (passenger == null) {
                log.info("No passengers were found for flight={} with Id={}", flightId, passengerId);
                model.addAttribute("notFound", "Passenger " + passengerId + " was Not Found");
            }
        } else {
            log.info("No bookings were found for flight={} with Id={}", flightId, passengerId);
            model.addAttribute("notFound", "Flight " + flightId + " was Not Found");
        }
        return "verifyBooking";

    }

    @PostMapping("/flight/book/cancel")
    public String cancelTicket(@RequestParam("passengerId") long passengerId, Model model) {
        log.info("About to cancel a booking for passenger {}", passengerId);
        passengerService.deleteById(passengerId);
        model.addAttribute("flights", flightService.getAllPaged(0));
        model.addAttribute("currentPage", 0);
        return "flights";
    }

    @GetMapping("passengers")
    public String showPassengerList(@RequestParam long flightId, Model model) {
        log.info("About to show the list of passengers for flights {}", flightId);
        List<Passenger> passengers = flightService.getById(flightId).getPassengers();
        model.addAttribute("passengers", passengers);
        model.addAttribute("flight", flightService.getById(flightId));
        return "passengers";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        log.info("Ola-ola-ola-ola, somebody is going to login! Probably... .");
        return "login";
    }


    @GetMapping("fancy")
    public String showLoginPage1() {
        log.info("Ola-ola-ola-ola, somebody is going to login somewhere!");
        return "index";
    }
}
