package br.com.clinica.dao;

import java.util.List;

public interface GenericDAO<T> {

    int insert(T object);

    void update(T object);

    T find(Integer id);

    List<T> findAll();

    void delete(Integer id);
}
