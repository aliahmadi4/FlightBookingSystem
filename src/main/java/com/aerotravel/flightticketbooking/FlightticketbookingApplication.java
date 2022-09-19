package com.aerotravel.flightticketbooking;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.AircraftDto;
import com.aerotravel.flightticketbooking.model.dto.FlightDto;
import com.aerotravel.flightticketbooking.model.dto.PassengerDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.stream.Collectors;

@EnableJpaRepositories("com.aerotravel.flightticketbooking.repository")
@EntityScan("com.aerotravel.flightticketbooking.model")
@SpringBootApplication
public class FlightticketbookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightticketbookingApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();

        //TODO(L.E.) Find a better place for such mappings.
        setupPassengerMappings(mapper);
        setupFlightMappings(mapper);
        setupAircraftMappings(mapper);

        return mapper;
    }

    private void setupPassengerMappings(ModelMapper mapper) {
        PropertyMap<Passenger, PassengerDto> passengerToDtoMap = new PropertyMap<>() {
            protected void configure() {
                map().setFlightId(source.getFlight().getFlightId());
            }
        };
        mapper.addMappings(passengerToDtoMap);
    }

    private void setupFlightMappings(ModelMapper mapper) {
        TypeMap<Flight, FlightDto> flightTypeMap = mapper.createTypeMap(Flight.class, FlightDto.class);
        Converter<List<Passenger>, List<Long>> passengerConverter = ctx ->
                ctx.getSource()
                        .stream()
                        .map(Passenger::getPassengerId)
                        .collect(Collectors.toList());
        flightTypeMap.addMappings(mappr -> {
            mappr.using(passengerConverter).map(Flight::getPassengers, FlightDto::setPassengerIds);
            mappr.map(src -> src.getAircraft().getAircraftId(),
                    FlightDto::setAircraftId);
        });
    }

    private void setupAircraftMappings(ModelMapper mapper) {
        TypeMap<Aircraft, AircraftDto> aircraftTypeMap = mapper.createTypeMap(Aircraft.class, AircraftDto.class);
        Converter<List<Flight>, List<Long>> flightsConverter = ctx ->
                ctx.getSource()
                        .stream()
                        .map(Flight::getFlightId)
                        .collect(Collectors.toList());
        aircraftTypeMap.addMappings(mappr -> {
            mappr.using(flightsConverter).map(Aircraft::getFlights, AircraftDto::setFlightIds);
        });
    }
}