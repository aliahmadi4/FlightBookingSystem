package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.services.AirportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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
class AirportRestControllerTest {
    private final static String API_MAPPING = "/api/v0/airports";
    private final Airport airport = buildAirport(2534);
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AirportService service;

    @Test
    public void findAll_success() throws Exception {
        var airport2 = buildAirport(5658);
        airport2.setAirportCode("BB5659");
        List<Airport> records = List.of(airport, airport2);

        when(service.getAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].airportCode", is(airport.getAirportCode())))
                .andExpect(jsonPath("$[1].airportCode", is(airport2.getAirportCode())));
    }

    @Test
    public void findById_success() throws Exception {
        when(service.getById(airport.getAirportId())).thenReturn(airport);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/" + airport.getAirportId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.airportName", is(airport.getAirportName())))
                .andExpect(jsonPath("$.airportCode", is(airport.getAirportCode())));
    }

    @Test
    public void create_success() throws Exception {
        var data = buildAirport(7980, "CCSS");
        when(service.save(data)).thenReturn(data);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(API_MAPPING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.airportCode", is(data.getAirportCode())));
    }

    @Test
    public void update_success() throws Exception {
        var data = buildAirport(9596, "UUSS");
        when(service.getById(data.getAirportId())).thenReturn(data);
        when(service.save(data)).thenReturn(data);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_MAPPING + "/" + data.getAirportId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.airportCode", is(data.getAirportCode())));
    }

    @Test
    public void delete_success() throws Exception {
        when(service.getById(airport.getAirportId())).thenReturn(airport);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_MAPPING + "/" + airport.getAirportId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Airport buildAirport(long id, String code) {
        return Airport.builder()
                .airportId(id)
                .airportCode(code)
                .airportName("De CeBanduro")
                .city("Ceba")
                .country("Mars")
                .state("Kanally")
                .build();
    }

    private Airport buildAirport(long id) {
        return buildAirport(id, "DCBA");
    }
}