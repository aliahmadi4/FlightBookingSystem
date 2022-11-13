package com.aerotravel.flightticketbooking.services.aux;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.repository.AircraftRepository;
import com.aerotravel.flightticketbooking.repository.AirportRepository;
import com.aerotravel.flightticketbooking.repository.FlightRepository;
import com.aerotravel.flightticketbooking.repository.PassengerRepository;
import com.github.javafaker.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataGenService {
    public static final int ALMOST_UPPER_BOUND = 12;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    private final Faker faker = new Faker(Locale.ENGLISH);
    private final Name nameFaker = faker.name();
    private final Aviation aviaFaker = faker.aviation();
    private final DateAndTime dateFaker = faker.date();
    private final Address addressFaker = faker.address();

    @Autowired
    public DataGenService(AircraftRepository aircraftRepository, AirportRepository airportRepository, FlightRepository flightRepository, PassengerRepository passengerRepository) {
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    public Map<String, List<String>> generateAll() {
        log.info("About to generate and persist fake data.");
        Random rnd = new Random();

        // Use both existing and freshly generated data.
        createAircrafts(rnd);
        var aircrafts = aircraftRepository.findAll();

        createAirports();
        var airports = airportRepository.findAll();

        createFlights(rnd, aircrafts, airports);
        var flights = flightRepository.findAll();

        var passengers = createPassengers(rnd, flights);

        var data = new TreeMap<String, List<String>>();
        data.put("Aircrafts", aircrafts.stream().map(Aircraft::toShortString).collect(Collectors.toList()));
        data.put("Airports", airports.stream().map(Airport::toShortString).collect(Collectors.toList()));
        data.put("Flights", flights.stream().map(Flight::toShortString).collect(Collectors.toList()));
        data.put("Passengers", passengers.stream().map(Passenger::toString).collect(Collectors.toList()));

        return data;
    }

    public List<Aircraft> createAircrafts(Random rnd) {
        log.info("About to create fake aircrafts.");
        List<Aircraft> aircrafts = new ArrayList<>();
        for (int i = 0; i < ALMOST_UPPER_BOUND; i++) {
            String model = aviaFaker.aircraft();
            var aircraft = Aircraft.builder()
                    .model(model)
                    .manufacturer(model.split("-|\\s")[0])
                    .numberOfSeats(1 + Math.abs(rnd.nextInt(850)))
                    .build();

            aircrafts.add(aircraft);
        }

        final var tupolev = "Tupolev";
        final var il = "Ilushin";
        final var cyxou = "Cyxou";
        final var yakovlev = "Yakovlev";
        final var airbus = "Airbus";
        final var boeing = "Boeing";

        aircrafts.add(new Aircraft(tupolev, "Tu-214", 150));
        aircrafts.add(new Aircraft(tupolev, "Tu-154", 140));
        aircrafts.add(new Aircraft(tupolev, "Tu-154M", 140));
        aircrafts.add(new Aircraft(tupolev, "Tu-134", 110));
        aircrafts.add(new Aircraft(tupolev, "Tu-114", 100));
        aircrafts.add(new Aircraft(il, "Il-86", 230));
        aircrafts.add(new Aircraft(il, "Il-96", 240));
        aircrafts.add(new Aircraft(il, "Il-76", 60));
        aircrafts.add(new Aircraft(il, "Il-18", 40));
        aircrafts.add(new Aircraft(il, "Il-103", 3));
        aircrafts.add(new Aircraft(il, "Il-2", 1));
        aircrafts.add(new Aircraft(cyxou, "SSJ", 99));
        aircrafts.add(new Aircraft(cyxou, "SSJ-N", 100));
        aircrafts.add(new Aircraft(yakovlev, "Yak-242", 150));
        aircrafts.add(new Aircraft(yakovlev, "Yak-42", 50));
        aircrafts.add(new Aircraft(airbus, "A-319", 99));
        aircrafts.add(new Aircraft(airbus, "A-320", 110));
        aircrafts.add(new Aircraft(airbus, "A-321", 120));
        aircrafts.add(new Aircraft(airbus, "A-340", 220));
        aircrafts.add(new Aircraft(airbus, "A-350", 220));
        aircrafts.add(new Aircraft(airbus, "A-380", 820));
        aircrafts.add(new Aircraft(boeing, "B-737", 150));
        aircrafts.add(new Aircraft(boeing, "B-747", 300));
        aircrafts.add(new Aircraft(boeing, "B-757", 300));
        aircrafts.add(new Aircraft(boeing, "B-777", 250));
        aircrafts.add(new Aircraft(boeing, "B-787", 350));

        return aircraftRepository.saveAll(aircrafts);
    }

    public List<Airport> createAirports() {
        log.info("About to create fake airports.");
        List<Airport> data = new ArrayList<>();
        var codes = new ArrayList<String>();
        for (int i = 0; i < ALMOST_UPPER_BOUND; i++) {
            var code = aviaFaker.airport();
            if (codes.contains(code)) {
                code += code + "`-M";
            }
            codes.add(code);

            var entry = Airport.builder()
                    .airportCode(code)
                    .airportName(faker.funnyName().name())
                    .city(addressFaker.city())
                    .state(addressFaker.state())
                    .country(addressFaker.country())
                    .build();

            data.add(entry);
        }

        data.add(new Airport("DAL", "Dallas Love Field", "Dallas", "Dallas", "United States"));
        data.add(new Airport("DCG", "Dubai Creek SPB", "Dubai", "Dubai", "United Arab Emirates"));
        data.add(new Airport("CID", "Cedar Rapid Airport", "IOWA", "United States", "Iowa"));
        data.add(new Airport("CHI", "Chicago Airport", "Chicago", "Illinois", "United States"));
        data.add(new Airport("CLN", "California Airport", "California", "California", "United States"));
        data.add(new Airport("TEX", "Texas Airport", "Texas", "Texas", "United States"));

        var existingData = airportRepository.findAll();
        var existingCodes = existingData.stream()
                .map(Airport::getAirportCode)
                .collect(Collectors.toList());
        var filteredData = data.stream()
                .filter(a-> !existingCodes.contains(a.getAirportCode())).collect(Collectors.toList());
        try {
            return airportRepository.saveAll(filteredData);
        } catch (Exception e) {
            if (null != e.getCause()
                    && null != e.getCause().getCause()
                    &&
                    e.getCause()
                            .getCause()
                            .getMessage().contains("Duplicate entry")) {
                log.error("Seems there are duplicate airport code upon fake data generating.", e);
                return List.of();
            }
            throw new RuntimeException(e);
        }
    }

    public List<Flight> createFlights(Random rnd, List<Aircraft> aircrafts, List<Airport> airports) {
        log.info("About to create fake flights.");
        List<Flight> data = new ArrayList<>();
        var aircraftsCount = aircrafts.size();
        var airportsCount = airports.size();
        for (int i = 0; i < ALMOST_UPPER_BOUND; i++) {
            LocalDate depDate = LocalDate.ofInstant(dateFaker.future(1 + Math.abs(rnd.nextInt(144)), TimeUnit.DAYS).toInstant(),
                    ZoneId.systemDefault());

            var entry = Flight.builder()
                    .flightNumber(faker.regexify("[A-Z]{2}-\\d{4}"))
                    .flightCharge(Double.MAX_EXPONENT * rnd.nextDouble())
                    .aircraft(aircrafts.get(Math.abs(rnd.nextInt(aircraftsCount))))
                    .departureDate(depDate)
                    .arrivalDate(depDate.plus(1 + rnd.nextInt(2), ChronoUnit.DAYS))
                    .departureTime(dateFaker.future(1 + rnd.nextInt(24), TimeUnit.HOURS).toString())
                    .arrivalTime(dateFaker.future(2 + rnd.nextInt(24), TimeUnit.HOURS).toString())
                    .departureAirport(airports.get(rnd.nextInt(airportsCount)))
                    .destinationAirport(airports.get(rnd.nextInt(airportsCount)))
                    .build();

            data.add(entry);
        }

        return flightRepository.saveAll(data);
    }

    public List<Passenger> createPassengers(Random rnd, List<Flight> flights) {
        log.info("About to create fake passengers.");
        List<Passenger> data = new ArrayList<>();
        var flightsCount = flights.size();
        for (int i = 0; i < ALMOST_UPPER_BOUND; i++) {
            LocalDate depDate = LocalDate.ofInstant(dateFaker.future(1 + Math.abs(rnd.nextInt(144)), TimeUnit.DAYS).toInstant(),
                    ZoneId.systemDefault());

            var entry = Passenger.builder()
                    .flight(flights.get(Math.abs(rnd.nextInt(flightsCount))))
                    .firstName(nameFaker.firstName())
                    .lastName(nameFaker.lastName())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .email(faker.internet().safeEmailAddress())
                    .passportNumber(faker.regexify("[0-9]{4} \\d{6}"))
                    .address(addressFaker.fullAddress())
                    .build();

            data.add(entry);
        }

        return passengerRepository.saveAll(data);
    }
}
