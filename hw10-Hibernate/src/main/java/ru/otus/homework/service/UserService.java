package ru.otus.homework.service;

import org.hibernate.Hibernate;
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
        dbTemplate.create(user);
        return user.getId();

    }

    @Override
    public User load(Long id) throws SQLException {
        User load = dbTemplate.load(id, User.class);
        Hibernate.initialize(load.getPhoneDataSet());
        return load;
    }

    @Override
    public void update(User user) throws SQLException, IllegalAccessException {
        dbTemplate.update(user);
    }
}
