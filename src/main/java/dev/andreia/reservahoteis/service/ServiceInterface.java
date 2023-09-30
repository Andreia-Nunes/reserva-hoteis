package dev.andreia.reservahoteis.service;

import java.util.List;

public interface ServiceInterface<T>{

    T findById(Long id);
    List<T> findAll();
    T save(T objectToSave);
    T update(Long id, T objectUpdated);
    void delete(Long id);

}
