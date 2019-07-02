package ru.otus.homework.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.database.HibernateTemplate;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.services.database.DbService;

import java.sql.SQLException;

class UserServiceTest {

    private DbService<User, Long> userService;
    private TestService testService;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        testService = new TestService(this);
        expectedUser = new User(
                1L,
                testService.generateString(),
                testService.generateNumeric(),
                new AddressDataSet(
                        testService.generateString()
                ),
                new PhoneDataSet(
                        testService.generateString()
                )
        );
        userService = new UserService(new HibernateTemplate());
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        long actualId = userService.create(expectedUser);
        Assertions.assertEquals(expectedUser.getId(), actualId);
    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        long id = userService.create(expectedUser);
        User actualUser = userService.load(id);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        userService.create(expectedUser);
        expectedUser = new User(
                expectedUser.getId(),
                testService.generateString(),
                testService.generateNumeric(),
                new AddressDataSet(
                        testService.generateString()
                ),
                new PhoneDataSet(
                        testService.generateString()
                )
        );
        userService.update(expectedUser);
        User actualUser = userService.load(expectedUser.getId());
        Assertions.assertEquals(expectedUser, actualUser);
    }
}