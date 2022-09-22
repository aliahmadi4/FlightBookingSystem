package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Flight;
import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.model.dto.PassengerDto;
import com.aerotravel.flightticketbooking.services.FlightService;
import com.aerotravel.flightticketbooking.services.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles="ADMIN")
class PassengerRestControllerTest {
    private final static String API_MAPPING = "/api/v0/passengers";
    private final Passenger entity = buildRecord(3638);
    @Autowired
    ObjectMapper mapper;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PassengerService service;
    @MockBean
    private FlightService flightService;

    @Test
    public void findAll_success() throws Exception {
        var entity2 = buildRecord(4041, "Olov");
        List<Passenger> records = List.of(entity, entity2);

        when(service.getAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].lastName", is(entity.getLastName())))
                .andExpect(jsonPath("$[0].firstName", is(entity.getFirstName())))
                .andExpect(jsonPath("$[1].firstName", is(entity2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(entity2.getLastName())));
    }

    @Test
    public void findById_success() throws Exception {
        when(service.getById(entity.getPassengerId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/" + entity.getPassengerId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is(entity.getLastName())))
                .andExpect(jsonPath("$.firstName", is(entity.getFirstName())))
                .andExpect(jsonPath("$.passportNumber", is(entity.getPassportNumber())))
                .andExpect(jsonPath("$.email", is(entity.getEmail())));
    }

    @Test
    public void create_success() throws Exception {
        var entity = buildRecord(8889, "Telegin");
        var dto = toDto(entity);
        when(service.save(Mockito.any(Passenger.class))).thenReturn(entity);
        when(flightService.getById(dto.getFlightId())).thenReturn(entity.getFlight());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(API_MAPPING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is(entity.getLastName())))
                .andExpect(jsonPath("$.firstName", is(entity.getFirstName())))
                .andExpect(jsonPath("$.passportNumber", is(entity.getPassportNumber())))
                .andExpect(jsonPath("$.email", is(entity.getEmail())));
    }

    @Test
    public void update_success() throws Exception {
        var entity = buildRecord(109110, "Obnovlentsev");
        var dto = toDto(entity);
        when(service.save(Mockito.any(Passenger.class))).thenReturn(entity);
        when(flightService.getById(dto.getFlightId())).thenReturn(entity.getFlight());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_MAPPING + "/" + dto.getPassengerId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is(entity.getLastName())))
                .andExpect(jsonPath("$.firstName", is(entity.getFirstName())))
                .andExpect(jsonPath("$.passportNumber", is(entity.getPassportNumber())))
                .andExpect(jsonPath("$.email", is(entity.getEmail())));
    }

    @Test
    public void delete_success() throws Exception {
        when(service.getById(entity.getPassengerId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_MAPPING + "/" + entity.getPassengerId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private Passenger buildRecord(long id, String surname) {
        return Passenger.builder()
                .passengerId(id)
                .lastName(surname)
                .firstName("Vasja" + Math.random())
                .email(Math.random() + "@test.m2p.tst")
                .address("pl. L. Tolstogo 12")
                .passportNumber("1029384756")
                .phoneNumber("+01234567890")
                .flight(Flight.builder().flightId(168177).flightNumber("PP-3975").build())
                .build();
    }

    private Passenger buildRecord(long id) {
        return buildRecord(id, "Pereletov");
    }

    private PassengerDto toDto(Passenger entity) {
        return modelMapper.map(entity, PassengerDto.class);
    }
}