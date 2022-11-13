package com.aerotravel.flightticketbooking.rest;

import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class VersionRestController {

    @GetMapping("/version")
    @Operation(summary = "Returns application version.")
    public ResponseEntity<String> getVersionInfo() {
        var faker = new Faker(Locale.getDefault());
        // TODO(L.E.): Invent something more elegant one day.
        return ResponseEntity.ok(
                String.format("As of %s the application version is 0.4 and the API version is 0.0 \n\n" +
                                "Meanwhile %s thinks it is time to read '%s'. In any case '%s'.",
                        LocalDateTime.now(),
                        faker.cat().name(),
                        faker.book().title(),
                        faker.shakespeare().hamletQuote()));
    }
}
