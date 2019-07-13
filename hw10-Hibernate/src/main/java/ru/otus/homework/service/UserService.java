package ru.otus.homework.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;

public class UserService implements DbService<User, Long> {
    private SessionFactory sessionFactory;
    private Cache<User> cache;

    public UserService(SessionFactory sessionFactory, Cache cache) {
        this.sessionFactory = sessionFactory;
        this.cache = cache;
    }

    @Override
    public long create(User user) {
        cache.put(user);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        return user.getId();
    }

    @Override
    public User load(Long id) {
        User userFromCahce = null;
        for (User userEntry : cache.getAll()) {
            if (userEntry.getId().equals(id)) {
                userFromCahce = userEntry;
                break;
            }
        }
        User loadedUser = cache.get(userFromCahce);
        if (loadedUser != null) {
            return loadedUser;
        }
        try (Session session = sessionFactory.openSession()) {
            loadedUser = session.get(User.class, id);
            return loadedUser;
        }
    }

    @Override
    public void update(User user) {
        for (User userEntry : cache.getAll()) {
            if (userEntry.getId().equals(user.getId())) {
                cache.remove(userEntry);
                cache.put(user);
                break;
            }
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }
}
