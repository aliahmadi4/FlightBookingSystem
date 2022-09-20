package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.dto.IdedEntity;
import com.aerotravel.flightticketbooking.services.EntityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
@Slf4j
public abstract class AbstractRestController<E, D extends IdedEntity> {

    @Autowired
    protected ModelMapper modelMapper;

    protected abstract EntityService<E> getService();
    protected abstract D convertToDto(E entity);
    protected abstract E convertToEntity(D entityDto);

    @GetMapping
    @Operation(summary = "Get all entities available.")
    public Iterable<D> findAll() {
        log.debug("Getting all records.");
        return getService()
                .getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/page/{number}")
    @Operation(summary = "Get entities available on the page.")
    public Iterable<D> findAllPaged(@PathVariable int number) {
        log.debug("Getting all records on page {}.", number);
        return getService()
                .getAllPaged(number)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an entity by its id.")
    public D findById(@PathVariable Long id) {
        log.info("Getting a record by id={}.", id);
        return convertToDto(getService().getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Attempt to create an entity by using its DTO.")
    public D create(@RequestBody D entityDto) {
        log.info("Attempting to create a record. {}", entityDto);
        return convertToDto(getService()
                .save(convertToEntity(entityDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Attempt to update an entity by using its DTO.")
    public D update(@RequestBody D entityDto, @PathVariable Long id) {
        if (entityDto.getId() != id) {
            throw new IllegalArgumentException("Ids mismatch.");
        }
        log.info("Attempting to update the record. {}", entityDto);
        // Check whether it exists at all.
        getService().getById(id);

        //TODO: Think about merging not re-writing.
        return create(entityDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Attempt to delete an entity by its id.")
    public void delete(@PathVariable Long id) {
        log.info("Attempting to delete record {}", id);
        // Check whether it exists at all.
        getService().getById(id);
        getService().deleteById(id);
    }
}

