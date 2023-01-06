package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EntityService<E> {
    Page<E> getAllPaged(int pageNum);

    Page<E> getPaged(Pageable pageable);

    List<E> getAll();

    E getById(Long id);

    Optional<E> getOptionallyById(Long id);

    List<E> getAllById(List<Long> ids);

    E save(E entity);

    void deleteById(Long id);

    default EntityNotFoundException buildEntityNotFoundException(String entityPrettyName, long id) {
        return new EntityNotFoundException(entityPrettyName,
                String.format("%s was not found by id=%s", entityPrettyName, id));
    }

    EntityNotFoundException buildEntityNotFoundException(long id);
}
