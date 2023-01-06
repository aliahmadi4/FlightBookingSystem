package com.aerotravel.flightticketbooking.rest.v0.aux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v0/aux/debug")
@Tag(name = "Debug", description = "Debug assistance")
@Slf4j
public class DebugRestController {

    @Autowired
    private Environment environment;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/show")
    @Operation(summary = "Get debug info.")
    public String generate() throws JsonProcessingException {
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
                        || prop.contains("PATH")
                        || prop.contains("OLDPWD")
                        || prop.contains("PWD")
                        || prop.contains("SHELL")
                        || prop.contains("_FLAGS")
                        || prop.contains("server.error.")
                        || prop.contains("spring.m")
                        || prop.contains("spring.jpa")
                        || prop.contains("spring.freem")
                        || prop.contains("spring.t")
                        || prop.contains("spring.g")
                        || prop.contains("spring.web.")
                        || prop.contains("spring.datasource.username")
                        || prop.contains("user.")
                        || prop.contains("java.")
                        || prop.contains("LOGNAME")))
                .forEach(prop -> data.put(prop, environment.getProperty(prop)));

        var result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        log.info("Debug info requested: {}", result);
        return result;
    }
}
