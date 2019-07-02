package ru.otus.homework.services.database;

import java.sql.SQLException;

public interface JdbcTemplate {
    <T> long create(T objectData) throws SQLException, IllegalAccessException;

    <T> void update(T objectData) throws IllegalAccessException, SQLException;

    <T> T load(long id, Class clazz) throws SQLException;
}
