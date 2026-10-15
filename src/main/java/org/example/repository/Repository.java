package org.example.repository;

import java.sql.SQLException;
import java.util.List;

// <T> tipo generico
// para ser implementado en diferentes clases y con diferentes tipos de datos
public interface Repository <T> {
    List<T> findAll() throws SQLException;

    T getById(Integer id) throws SQLException;

    // insert | update
    void save(T t) throws SQLException;

    void delete(Integer id) throws SQLException;
}
