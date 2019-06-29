package ru.otus.homework.services.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.models.User;
import ru.otus.homework.services.database.Param;

import java.util.ArrayList;
import java.util.List;

class ReflectionServiceImplTest {
    private ReflectionService reflectionService = new ReflectionServiceImpl();
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "2", 3);
    }

    @Test
    void getClassName() {
        Assertions.assertEquals(
                reflectionService.getClassName(testUser),
                testUser.getClass().getSimpleName()
        );
    }

    @Test
    void getTypeValueMap() throws IllegalAccessException {
        List<Param> expected = new ArrayList<>();
        expected.add(new Param("name", "2"));
        expected.add(new Param("age", "3"));
        List<Param> actual = reflectionService.getFieldsExceptIdAsParams(testUser);
        Assertions.assertEquals(expected, actual);
    }
}