package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

abstract class AbstractEntityServiceImpl<E> implements EntityService<E> {

    private static final int PAGE_SIZE = 5;

    protected abstract JpaRepository<E, Long> getRepository();

    protected abstract String[] getSortByProperties();

    @Override
    public Page<E> getAllPaged(int pageNum) {
        return getRepository().findAll(PageRequest.of(pageNum, PAGE_SIZE, Sort.by(getSortByProperties())));
    }

    @Override
    public Page<E> getPaged(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public List<E> getAll() {
        return getRepository().findAll();
    }

    @Override
    public E getById(Long entityId) {
        if (null == entityId) throw new IllegalArgumentException("Entity ID shall be null.");

        return getRepository().findById(entityId).orElseThrow(() -> buildEntityNotFoundException(entityId));
    }

    @Override
    public Optional<E> getOptionallyById(Long entityId) {
        if (null == entityId) throw new IllegalArgumentException("Entity ID shall be null.");

        return getRepository().findById(entityId);
    }

    @Override
    public List<E> getAllById(List<Long> entityIds) {
        if (null == entityIds) throw new IllegalArgumentException("List of Entity IDs shall be null.");

        return getRepository().findAllById(entityIds);
    }

    @Override
    public E save(E entity) {
        if (null == entity) throw new IllegalArgumentException("Cannot save null!");
        return getRepository().save(entity);
    }

    @Override
    public void deleteById(Long entity) {
        getRepository().deleteById(entity);
    }

}
