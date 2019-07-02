package ru.otus.homework.service;

import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;
import ru.otus.homework.services.database.DbTemplate;

import java.sql.SQLException;

public class UserService implements DbService<User, Long> {
    private DbTemplate dbTemplate;

    public UserService(DbTemplate dbTemplate) {
        this.dbTemplate = dbTemplate;
    }

    @Override
    public long create(User user) throws SQLException, IllegalAccessException {
        return dbTemplate.create(user);
    }

    @Override
    public User load(Long id) throws SQLException {
        return dbTemplate.load(id, User.class);
    }

    @Override
    public void update(User user) throws SQLException, IllegalAccessException {
        dbTemplate.update(user);
    }
}
