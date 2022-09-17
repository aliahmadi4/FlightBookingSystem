package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

abstract class AbstractEntityServiceImpl<E> implements EntityService<E> {

    private static final int PAGE_SIZE = 5;

    protected abstract JpaRepository<E, Long> getRepository();

    protected abstract String[] getSortByProperties();

    @Override
    public Page<E> getAllPaged(int pageNum) {
        return getRepository().findAll(PageRequest.of(pageNum, PAGE_SIZE, Sort.by(getSortByProperties())));
    }

    @Override
    public List<E> getAll() {
        return getRepository().findAll();
    }

    @Override
    public E getById(Long entityId) {
        return getRepository().findById(entityId).orElseThrow(() -> buildEntityNotFoundException(entityId));
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
