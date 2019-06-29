package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;
import ru.otus.homework.services.testservices.TestService;

import java.sql.SQLException;

class JdbcTemplateTest {
    private JdbcTemplate jdbcTemplate;
    private TestService testService;

    @BeforeEach
    void setUp() throws SQLException {
        testService = new TestService(this);
        H2DbServiceImpl h2DbService = new H2DbServiceImpl();
        h2DbService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
        jdbcTemplate = new JdbcTemplate(
                new ReflectionServiceImpl(),
                h2DbService
        );
    }

    @Test
    void creteLoadUpdateLoad() throws SQLException, IllegalAccessException {
        User expected = new User(
                3L,
                testService.generateTestName(),
                testService.generateAge()
        );
        jdbcTemplate.create(expected);
        User actual = jdbcTemplate.load(1, User.class);
        Assertions.assertEquals(expected, actual);
        expected = new User(
                actual.getId(),
                testService.generateTestName(),
                testService.generateAge()
        );
        jdbcTemplate.update(expected);
        actual = jdbcTemplate.load(expected.getId(), User.class);
        Assertions.assertEquals(expected, actual);
    }
}