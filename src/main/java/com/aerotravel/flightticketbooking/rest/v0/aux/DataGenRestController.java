package com.aerotravel.flightticketbooking.rest.v0.aux;

import com.aerotravel.flightticketbooking.services.aux.DataGenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/aux/data")
@Slf4j
public class DataGenRestController {
    private final DataGenService dataGenService;

    @Autowired
    public DataGenRestController(DataGenService dataGenService) {
        this.dataGenService = dataGenService;
    }

    @GetMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generate some random data. The response should contain some description on what was generated.")
    public Map<String, List<String>> generate() {
        return dataGenService.generateAll();
    }
}
