package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.dto.AircraftDto;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class AircraftRestControllerTest {
    private final static String API_MAPPING = "/api/v0/aircrafts";
    private final Aircraft entity = buildAircraft(2527, "SSJ");
    @Autowired
    ObjectMapper mapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AircraftService service;

    @Test
    public void findByModel_success() throws Exception {
        var data = buildAircraft(3637, "SSJ-100");
        when(service.getByModel(data.getModel())).thenReturn(List.of(data));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/model/" + data.getModel())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is(data.getModel())))
                .andExpect(jsonPath("$[0].manufacturer", is(data.getManufacturer())));
    }

    @Test
    public void findByManufacturer_success() throws Exception {
        var data = buildAircraft(5152, "Tu-204");
        data.setManufacturer("Tupolev");
        when(service.getByManufacturer(data.getManufacturer())).thenReturn(List.of(data));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/manufacturer/" + data.getManufacturer())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is(data.getModel())))
                .andExpect(jsonPath("$[0].manufacturer", is(data.getManufacturer())));
    }

    @Test
    public void findAll_success() throws Exception {
        var aircraft = buildAircraft(3637, "11");
        aircraft.setManufacturer("Delfin");
        List<Aircraft> records = List.of(entity, aircraft);
        when(service.getAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].model", is(entity.getModel())))
                .andExpect(jsonPath("$[1].model", is(aircraft.getModel())));
    }

    @Test
    public void findById_success() throws Exception {
        when(service.getById(entity.getAircraftId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(API_MAPPING + "/" + entity.getAircraftId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.model", is(entity.getModel())))
                .andExpect(jsonPath("$.manufacturer", is(entity.getManufacturer())));
    }

    @Test
    public void create_success() throws Exception {
        var data = buildAircraft(6667, "CCSS");
        var dto = modelMapper.map(data, AircraftDto.class);
        when(service.save(data)).thenReturn(data);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(API_MAPPING)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.model", is(data.getModel())));
    }

    @Test
    public void update_success() throws Exception {
        var data = buildAircraft(8283, "UUSS");
        var dto = modelMapper.map(data, AircraftDto.class);
        when(service.getById(data.getAircraftId())).thenReturn(data);
        when(service.save(data)).thenReturn(data);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(API_MAPPING + "/" + data.getAircraftId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.model", is(data.getModel())));
    }

    @Test
    public void delete_success() throws Exception {
        when(service.getById(entity.getAircraftId())).thenReturn(entity);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(API_MAPPING + "/" + entity.getAircraftId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Aircraft buildAircraft(long id, String model) {
        return Aircraft.builder()
                .aircraftId(id)
                .model(model)
                .manufacturer("Cyxou")
                .numberOfSeats(278)
                .build();
    }
}
