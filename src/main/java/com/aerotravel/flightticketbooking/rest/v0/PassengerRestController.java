package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.PassengerDto;
import com.aerotravel.flightticketbooking.services.EntityService;
import com.aerotravel.flightticketbooking.services.FlightService;
import com.aerotravel.flightticketbooking.services.PassengerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/passengers")
@Tag(name = "Passenger", description = "Passenger resource.")
@Validated
public class PassengerRestController extends AbstractRestController<Passenger, PassengerDto> {
    private final PassengerService passengerService;

    private final FlightService flightService;

    @Autowired
    public PassengerRestController(PassengerService passengerService, FlightService flightService) {
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @Override
    protected EntityService<Passenger> getService() {
        return passengerService;
    }

    @Override
    protected Class<Passenger> getEntityClass() {
        return Passenger.class;
    }

    @Override
    protected PassengerDto convertToDto(Passenger entity) {
        return modelMapper.map(entity, PassengerDto.class);
    }

    @Override
    protected Passenger convertToEntity(PassengerDto entityDto) {
        var candidate = modelMapper.map(entityDto, Passenger.class);
        candidate.setFlight(flightService.getById(entityDto.getFlightId()));

        return candidate;
    }
}
