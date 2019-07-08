package ru.otus.homework.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.homework.services.database.DbTemplate;

public class HibernateTemplate implements DbTemplate {

    private SessionFactory sessionFactory;

    public HibernateTemplate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public <T> long create(T objectData) {
       Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(objectData);
            session.getTransaction().commit();
        return 0;
    }

    @Override
    public <T> void update(T objectData) {
        Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(objectData);
            session.getTransaction().commit();
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(clazz, id);
    }
}
