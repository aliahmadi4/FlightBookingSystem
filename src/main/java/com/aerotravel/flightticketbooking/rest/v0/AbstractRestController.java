package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.dto.IdedEntity;
import com.aerotravel.flightticketbooking.services.EntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.stream.Collectors;

public abstract class AbstractRestController<E, D extends IdedEntity> {

    @Autowired
    protected ModelMapper modelMapper;

    protected abstract EntityService<E> getService();
    protected abstract D convertToDto(E entity);
    protected abstract E convertToEntity(D entityDto);

    @GetMapping
    @Operation(summary = "Get all entity available.")
    public Iterable<D> findAll() {
        return getService()
                .getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an entity by its id.")
    public D findById(@PathVariable Long id) {
        return convertToDto(getService().getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Attempt to create an entity by using its DTO.")
    public D create(@RequestBody D entityDto) {
        return convertToDto(getService()
                .save(convertToEntity(entityDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Attempt to update an entity by using its DTO.")
    public D update(@RequestBody D entityDto, @PathVariable Long id) {
        if (entityDto.getId() != id) {
            throw new IllegalArgumentException("Ids mismatch.");
        }
        // Check whether it exists at all.
        getService().getById(id);

        //TODO: Think about merging not re-writing.
        return create(entityDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Attempt to delete an entity by its id.")
    public void delete(@PathVariable Long id) {
        // Check whether it exists at all.
        getService().getById(id);
        getService().deleteById(id);
    }
}

