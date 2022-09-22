package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.FlightDto;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.aerotravel.flightticketbooking.services.AirportService;
import com.aerotravel.flightticketbooking.services.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles="ADMIN")
class FlightRestControllerTest {
    private final static String API_MAPPING = "/api/v0/flights";
    private final Flight entity = buildRecord(2830);
    @Autowired
    ObjectMapper mapper;
    @Autowired private MockMvc mockMvc;
    @MockBean
    private FlightService service;
    @MockBean
    private AircraftService aircraftService;
    @MockBean
    private AirportService airportService;

    @Test
    public void findAll_success() throws Exception {
        var entity2 = buildRecord(4041, "YQ4041");
        List<Flight> records = List.of(entity, entity2);

        when(service.getAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].flightNumber", is(entity.getFlightNumber())))
                .andExpect(jsonPath("$[1].flightNumber", is(entity2.getFlightNumber())));
    }

    @Test
    public void findById_success() throws Exception {
        when(service.getById(entity.getFlightId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/" + entity.getFlightId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.flightNumber", is(entity.getFlightNumber())))
                .andExpect(jsonPath("$.aircraftId", notNullValue()))
                .andExpect(jsonPath("$.departureAirportCode", notNullValue()))
                .andExpect(jsonPath("$.destinationAirportCode", notNullValue()))
                .andExpect(jsonPath("$.departureDate", notNullValue()))
                .andExpect(jsonPath("$.arrivalDate", notNullValue()));
    }

    @Test
    public void create_success() throws Exception {
        var entity = buildRecord(7476, "WW-CCSS");
        var dto = toDto(entity);
        when(service.save(Mockito.any(Flight.class))).thenReturn(entity);
        when(airportService.getByCode(entity.getDepartureAirport().getAirportCode())).thenReturn(entity.getDepartureAirport());
        when(airportService.getByCode(entity.getDestinationAirport().getAirportCode())).thenReturn(entity.getDestinationAirport());
        when(aircraftService.getById(dto.getAircraftId())).thenReturn(Aircraft.builder().aircraftId(8185).build());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(API_MAPPING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.flightId", notNullValue()))
                .andExpect(jsonPath("$.aircraftId", notNullValue()))
                .andExpect(jsonPath("$.departureAirportCode", is(dto.getDepartureAirportCode())))
                .andExpect(jsonPath("$.destinationAirportCode", is(dto.getDestinationAirportCode())))
                .andExpect(jsonPath("$.departureDate", notNullValue()))
                .andExpect(jsonPath("$.flightNumber", is(entity.getFlightNumber())));
    }

    @Test
    public void update_success() throws Exception {
        var entity = buildRecord(109110, "TY-UUSS");
        var dto = toDto(entity);
        when(service.getById(entity.getFlightId())).thenReturn(entity);
        when(service.save(Mockito.any(Flight.class))).thenReturn(entity);
        when(airportService.getByCode(entity.getDepartureAirport().getAirportCode())).thenReturn(entity.getDepartureAirport());
        when(airportService.getByCode(entity.getDestinationAirport().getAirportCode())).thenReturn(entity.getDestinationAirport());
        when(aircraftService.getById(dto.getAircraftId())).thenReturn(Aircraft.builder().aircraftId(8185).build());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_MAPPING + "/" + entity.getFlightId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.flightId", notNullValue()))
                .andExpect(jsonPath("$.aircraftId", notNullValue()))
                .andExpect(jsonPath("$.departureAirportCode", is(dto.getDepartureAirportCode())))
                .andExpect(jsonPath("$.destinationAirportCode", is(dto.getDestinationAirportCode())))
                .andExpect(jsonPath("$.departureDate", notNullValue()))
                .andExpect(jsonPath("$.flightNumber", is(entity.getFlightNumber())));
    }

    @Test
    public void delete_success() throws Exception {
        when(service.getById(entity.getFlightId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_MAPPING + "/" + entity.getFlightId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByFlightNumber_success() throws Exception {
        List<Flight> records = List.of(entity, buildRecord(144145, entity.getFlightNumber()));
        when(service.getAllByByFlightNumber(entity.getFlightNumber())).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/number/" + entity.getFlightNumber())
                        .with(user("admin").password("pass").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].flightNumber", is(entity.getFlightNumber())))
                .andExpect(jsonPath("$[0].aircraftId", notNullValue()))
                .andExpect(jsonPath("$[0].departureAirportCode", is(entity.getDepartureAirport().getAirportCode())))
                .andExpect(jsonPath("$[0].destinationAirportCode", is(entity.getDestinationAirport().getAirportCode())))
                .andExpect(jsonPath("$[0].departureDate", notNullValue()))
                .andExpect(jsonPath("$[0].arrivalDate", notNullValue()));
    }

    //TODO(L.E.) Implement the tests.
    @Disabled("Test - Not yet implemented.")
    @Test
    void findByAirportAndDepartureTime_success() {
    }

    @Disabled("Test - Not yet implemented.")
    @Test
    void verifyBooking_success() throws Exception {
        var flight = buildRecord(170171, "VB-2310");
        var passenger1 = Passenger.builder()
                .flight(flight)
                .passengerId(170174)
                .build();
        var passenger2 = Passenger.builder()
                .flight(flight)
                .passengerId(170178)
                .build();
        flight.setPassengers(List.of(passenger1, passenger2));
        when(service.getById(flight.getFlightId())).thenReturn(flight);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/book/verify")
                        .param("passengerId", String.valueOf(passenger2.getPassengerId()))
                        .param("flightId", String.valueOf(flight.getFlightId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.passengerId", is(passenger2.getPassengerId()), Long.class));
    }

    @Disabled("Test - Not yet implemented.")
    @Test
    void cancelBooking_success() {
    }

    private Flight buildRecord(long id, String number) {
        return Flight.builder()
                .flightId(id)
                .flightNumber(number)
                .flightCharge(Math.random())
                .aircraft(Aircraft.builder().aircraftId(120125).build())
                .departureAirport(Airport.builder().airportId(118123).airportCode("AAZZ").build())
                .destinationAirport(Airport.builder().airportId(118124).airportCode("ZZXX").build())
                .departureDate(LocalDate.now())
                .arrivalDate(LocalDate.now().plusDays(1))
                .build();
    }

    private Flight buildRecord(long id) {
        return buildRecord(id, "SU-526");
    }

    private FlightDto toDto(Flight entity) {
        return FlightDto.builder()
                .flightId(entity.getFlightId())
                .flightNumber(entity.getFlightNumber())
                .flightCharge(entity.getFlightCharge())
                .aircraftId(null == entity.getAircraft() ? 0L : entity.getAircraft().getAircraftId())
                .departureAirportCode(null == entity.getDepartureAirport() ? null : entity.getDepartureAirport().getAirportCode())
                .destinationAirportCode(null == entity.getDestinationAirport() ? null : entity.getDestinationAirport().getAirportCode())
                .departureDate(entity.getDepartureDate())
                .arrivalDate(entity.getArrivalDate())
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .passengerIds(null == entity.getPassengers() ? null :
                        entity.getPassengers().stream()
                                .filter(Objects::nonNull)
                                .map(Passenger::getPassengerId).collect(Collectors.toList()))
                .build();
    }
}