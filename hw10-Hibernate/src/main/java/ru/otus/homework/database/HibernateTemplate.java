package ru.otus.homework.database;

import ru.otus.homework.services.database.DbTemplate;

import java.sql.SQLException;

public class HibernateTemplate implements DbTemplate {
    @Override
    public <T> long create(T objectData) throws SQLException, IllegalAccessException {
        return 0;
    }

    @Override
    public <T> void update(T objectData) throws IllegalAccessException, SQLException {

    }

    @Override
    public <T> T load(long id, Class clazz) throws SQLException {
        return null;
    }
}
