package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.UserTestService;

import java.sql.SQLException;

class JdbcTemplateTest {
    private JdbcTemplate jdbcTemplate;
    private UserTestService userTestService;
    private User expected;

    @BeforeEach
    void setUp() throws SQLException {
        userTestService = new UserTestService(this);
        DbService h2DbService = new H2DbServiceImpl();
        h2DbService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
        jdbcTemplate = new JdbcTemplate(
                new ReflectionServiceImpl(),
                h2DbService
        );
        expected = new User(
                1L,
                userTestService.generateName(),
                userTestService.generateAge()
        );
    }

    @Test
    void create() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expected);
        Assertions.assertEquals(expected.getId(), id);
    }

    @Test
    void update() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expected);
        Assertions.assertEquals(expected.getId(), id);

        User actual = jdbcTemplate.load(expected.getId(), User.class);
        Assertions.assertEquals(expected, actual);
        expected = new User(
                actual.getId(),
                userTestService.generateName(),
                userTestService.generateAge()
        );
        jdbcTemplate.update(expected);
        actual = jdbcTemplate.load(expected.getId(), User.class);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void load() throws SQLException, IllegalAccessException {
        long id = jdbcTemplate.create(expected);
        Assertions.assertEquals(expected.getId(), id);

        User actual = jdbcTemplate.load(1, User.class);
        Assertions.assertEquals(expected, actual);
    }
}