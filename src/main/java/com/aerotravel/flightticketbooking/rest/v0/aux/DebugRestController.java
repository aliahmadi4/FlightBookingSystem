package com.aerotravel.flightticketbooking.rest.v0.aux;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v0/aux/debug")
@Slf4j
public class DebugRestController {

    @Autowired
    private Environment environment;

    @GetMapping("/show")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Get debug info.")
    public Map<String, Object> generate() {
        var data = new TreeMap<String, Object>();
        data.put("Active profiles", environment.getActiveProfiles());

        final MutablePropertySources sources = ((AbstractEnvironment) environment).getPropertySources();

        StreamSupport.stream(sources.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .filter(prop -> !(prop.contains("credentials")
                        || prop.contains("password")
                        || prop.contains("USER")
                        || prop.contains("user.")
                        || prop.contains("java.")
                        || prop.contains("LOGNAME")))
                .forEach(prop -> data.put(prop, environment.getProperty(prop)));

        return data;
    }
}
