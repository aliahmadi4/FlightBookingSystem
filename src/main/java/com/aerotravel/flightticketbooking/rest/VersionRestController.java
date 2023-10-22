package com.aerotravel.flightticketbooking.rest;

import com.aerotravel.flightticketbooking.model.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TreeMap;


@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Version", description = "This is the way to get app/API version info.")
public class VersionRestController {

    @GetMapping("/version")
    @Operation(summary = "Returns application version.")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ApiResponse> getVersionInfo() {
        log.info("Somebody asks about version number... .");
        var faker = new Faker(Locale.ENGLISH);

        var appV = "0.8";
        var apiV = "00.062";
        var headers = new HttpHeaders();
        headers.add("_FTB-version-app", appV);
        headers.add("_FTB-version-API", apiV);
        headers.add("_FTB-version-mood", faker.mood().emotion());
        headers.add("_FTB-version-tone", faker.mood().tone());

        var data = String.format("As of %s the application version is %s and the API version is %s and that is slightly amazing. ||   " +
                                " Meanwhile %s %s feels %s and thinks it is time to read '%s' by '%s'.  " +
                        "Or fly by %s to %s using %s on flight %s.",
                        LocalDateTime.now(),
                        appV,
                        apiV,
                        faker.cat().breed(), faker.cat().name(),
                        faker.mood().feeling(),
                        faker.book().title(),
                        faker.book().author(),
                        faker.aviation().airline(),
                        faker.aviation().airport(),
                        faker.aviation().aircraft(),
                        faker.aviation().flight());

        var details = new TreeMap<>(headers.toSingleValueMap());
        details.put("Heading", faker.compass().abbreviation());
        details.put("Driving azimuth", faker.compass().azimuth());
        details.put("Vehicle", faker.vehicle().makeAndModel());

        return new ResponseEntity<>(ApiResponse.of()
                .code(HttpStatus.FOUND.value())
                .message(data)
                .details(details)
                .build(),
                headers, HttpStatus.FOUND);
    }
}
