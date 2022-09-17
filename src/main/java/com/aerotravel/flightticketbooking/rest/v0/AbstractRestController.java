package com.aerotravel.flightticketbooking.rest.v0;

import com.aerotravel.flightticketbooking.model.IdedEntity;
import com.aerotravel.flightticketbooking.services.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public abstract class AbstractRestController<E extends IdedEntity> {

    protected abstract EntityService<E> getService();

    @GetMapping
    public Iterable<E> findAll() {
        return getService().getAll();
    }

    @GetMapping("/{id}")
    public E findById(@PathVariable Long id) {
        return getService().getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public E create(@RequestBody E entity) {
        return getService().save(entity);
    }

    @PutMapping("/{id}")
    public E update(@RequestBody E entity, @PathVariable Long id) {
        if (entity.getId() != id) {
            throw new IllegalArgumentException("Ids mismatch.");
        }
        // Check whether it exists at all.
        getService().getById(id);

        return getService().save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // Check whether it exists at all.
        getService().getById(id);
        getService().deleteById(id);
    }
}

