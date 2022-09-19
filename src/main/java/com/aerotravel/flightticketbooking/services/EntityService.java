package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntityService<E> {
    Page<E> getAllPaged(int pageNum);

    List<E> getAll();

    E getById(Long id);

    List<E> getAllById(List<Long> ids);

    E save(E entity);

    void deleteById(Long id);

    default EntityNotFoundException buildEntityNotFoundException(String entityPrettyName, long id) {
        return new EntityNotFoundException(entityPrettyName,
                String.format("%s was not found by id=%s", entityPrettyName, id));
    }

    EntityNotFoundException buildEntityNotFoundException(long id);
}
