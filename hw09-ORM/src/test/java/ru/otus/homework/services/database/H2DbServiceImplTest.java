package ru.otus.homework.services.database;

import org.junit.jupiter.api.*;
import ru.otus.homework.models.User;
import ru.otus.homework.services.reflection.ReflectionService;
import ru.otus.homework.services.reflection.ReflectionServiceImpl;

import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class H2DbServiceImplTest {
    private H2DbServiceImpl h2DbService;
    private ReflectionService reflectionService;
    private User expected;

    @AfterEach
    void tearDown() throws SQLException {
        h2DbService.close();
    }

    @BeforeEach
    void setUp() throws SQLException {
        h2DbService = new H2DbServiceImpl();
        reflectionService = new ReflectionServiceImpl();
        expected = new User(1L, "2", 3);
        h2DbService.execute("create table user(id long auto_increment, name varchar(50),  age int(3))");
    }

    @Test
    @Order(1)
    void insertRow() throws IllegalAccessException, SQLException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParams(expected));
        Assertions.assertEquals(1, userId);
    }


    @Order(2)
    @Test
    void selectRow() throws SQLException, IllegalAccessException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParams(expected));
        User actual = h2DbService.selectRow("User", userId, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            return new User(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("age")
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    void updateRow() throws SQLException, IllegalAccessException {
        long userId = h2DbService.insertRow("User", reflectionService.getFieldsExceptIdAsParams(expected));
        expected.setAge((int) Math.random());
        expected.setName(String.valueOf(Math.random()));
        h2DbService.updateRow("User",reflectionService.getFieldsExceptIdAsParams(expected), expected.getId());
        User actual = h2DbService.selectRow("User", userId, resultSet -> {
                    try {
                        if (resultSet.next()) {
                            return new User(
                                    resultSet.getLong("id"),
                                    resultSet.getString("name"),
                                    resultSet.getInt("age")
                            );
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
        );
        Assertions.assertEquals(expected, actual);
    }
}