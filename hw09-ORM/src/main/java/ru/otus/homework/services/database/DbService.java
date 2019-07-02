package ru.otus.homework.services.database;

import java.sql.SQLException;

public interface DbService<T,K> {
    long create(T target) throws SQLException, IllegalAccessException;
    T load(K id) throws SQLException;
    void update(T target) throws SQLException, IllegalAccessException;
}
