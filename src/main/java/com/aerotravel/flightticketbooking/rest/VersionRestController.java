package com.aerotravel.flightticketbooking.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;


@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Version", description = "This is the way to get app/API version info.")
public class VersionRestController {

    @GetMapping("/version")
    @Operation(summary = "Returns application version.")
    public ResponseEntity<String> getVersionInfo() {
        log.info("Somebody asks about version number... .");

        var appV = "0.75";
        var apiV = "00.06";
        var headers = new HttpHeaders();
        headers.add("_FTB-version-app", appV);
        headers.add("_FTB-version-API", apiV);

        var faker = new Faker(Locale.ENGLISH);
        // TODO(L.E.): Invent something more elegant one day.
        var resp = String.format("As of %s the application version is %s and the API version is %s \n\n" +
                                "Meanwhile %s %s thinks it is time to read '%s'.\n" +
                        "Or fly by %s to %s using %s on flight %s.",
                        LocalDateTime.now(),
                        appV,
                        apiV,
                        faker.cat().breed(), faker.cat().name(),
                        faker.book().title(),
                        faker.aviation().airline(),
                        faker.aviation().airport(),
                        faker.aviation().aircraft(),
                        faker.aviation().flight());

        return new ResponseEntity<>(resp, headers, HttpStatus.FOUND);
    }
}
