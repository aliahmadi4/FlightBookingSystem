package com.aerotravel.flightticketbooking.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("/api")
@Tag(name = "Version", description = "This is the way to get app/API version info.")
public class VersionRestController {

    @GetMapping("/version")
    @Operation(summary = "Returns application version.")
    public String getVersionInfo() {
        var faker = new Faker(Locale.ENGLISH);
        // TODO(L.E.): Invent something more elegant one day.
        return String.format("As of %s the application version is 0.7 and the API version is 00.05 \n\n" +
                                "Meanwhile %s %s thinks it is time to read '%s'. Or fly to %s using some %s.",
                        LocalDateTime.now(),
                        faker.cat().breed(), faker.cat().name(),
                        faker.book().title(),
                        faker.aviation().airport(),
                        faker.aviation().aircraft());
    }
}
