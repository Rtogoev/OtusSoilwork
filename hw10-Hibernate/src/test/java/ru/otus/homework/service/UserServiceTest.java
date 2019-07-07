package ru.otus.homework.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.database.HibernateTemplate;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;

import java.sql.SQLException;
import java.util.Collections;

class UserServiceTest {

    private DbService<User, Long> userService;
    private TestService testService;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(AddressDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        testService = new TestService(this);
        expectedUser = new User(
                "test",
                testService.generateNumeric(),
                new AddressDataSet("test"),
                Collections.singleton(
                        new PhoneDataSet("test")
                )
        );
        userService = new UserService(new HibernateTemplate(sessionFactory));
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        Assertions.assertEquals(1L, expectedUser.getId());
    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        assertEquals(expectedUser, actualUser);
    }

    private void assertEquals(User expectedUser, User actualUser) {
        Assertions.assertEquals(expectedUser.getId(),actualUser.getId());
        Assertions.assertEquals(expectedUser.getPhoneDataSet(),actualUser.getPhoneDataSet());
        Assertions.assertEquals(expectedUser.getAddressDataSet().getId(),actualUser.getAddressDataSet().getId());
        Assertions.assertEquals(expectedUser.getAddressDataSet().getStreet(),actualUser.getAddressDataSet().getStreet());
        Assertions.assertEquals(expectedUser.getAge(),actualUser.getAge());
        Assertions.assertEquals(expectedUser.getName(),actualUser.getName());
    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        expectedUser = new User(
                "test",
                testService.generateNumeric(),
                new AddressDataSet("test"),
                Collections.singleton(
                        new PhoneDataSet("test")
                )
        );
        userService.update(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        Assertions.assertEquals(expectedUser, actualUser);
    }
}