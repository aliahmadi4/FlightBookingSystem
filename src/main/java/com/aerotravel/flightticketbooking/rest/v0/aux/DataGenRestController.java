package com.aerotravel.flightticketbooking.rest.v0.aux;

import com.aerotravel.flightticketbooking.services.aux.DataGenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/aux/data")
@Slf4j
@Tag(name = "Data generator", description = "Generate additional fresh fake data for testing purposes.")
public class DataGenRestController {
    private final DataGenService dataGenService;

    @Autowired
    public DataGenRestController(DataGenService dataGenService) {
        this.dataGenService = dataGenService;
    }

    @GetMapping("/generate")
    @Operation(summary = "Generates some random data. The response should contain some description on what was generated.")
    public ResponseEntity<Map<String, List<String>>> generate() {
        var headers = new HttpHeaders();
        headers.add("_FTB-datagen-on", LocalDateTime.now().toString());

        return new ResponseEntity<>(dataGenService.generateAll(), headers, HttpStatus.CREATED);
    }
}
