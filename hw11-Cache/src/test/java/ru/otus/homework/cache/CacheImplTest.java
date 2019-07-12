package ru.otus.homework.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.TestService;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CacheImplTest {
    private Cache<User> cache;
    private TestService testService;
    private User expectedUser;
    
    @BeforeEach
    void setUp() {
        cache = new CacheImpl<>();
        testService = new TestService(this);
        expectedUser = new User(
                "test",
                testService.generateNumeric(),
                new AddressDataSet("test"),
                Collections.singleton(
                        new PhoneDataSet("test")
                )
        );
    }

    @Test
    void put_get() {
        cache.put(expectedUser);
        User actualUser = cache.get(expectedUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getHitCount() {
    }

    @Test
    void getMissCount() {
    }

    @Test
    void dispose() {
    }
}