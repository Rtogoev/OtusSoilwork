package ru.otus.homework.service;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;

public class UserService implements DbService<User, Long> {
    private SessionFactory sessionFactory;
    private Cache cache;

    public UserService( SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        return user.getId();
    }

    @Override
    public User load(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User loadUser = session.get(User.class, id);
            return loadUser;
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }
}
