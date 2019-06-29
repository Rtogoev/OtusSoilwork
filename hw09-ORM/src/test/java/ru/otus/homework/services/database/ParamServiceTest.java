package ru.otus.homework.services.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ParamServiceTest {
    private ParamService paramService = new ParamService();
    private List<Param> testParams;

    @BeforeEach
    void setUp() {
        testParams = new ArrayList<>();
        testParams.add(new Param("one","1"));
        testParams.add(new Param("two","2"));
        testParams.add(new Param("three","3"));
    }

    @Test
    void getValuesString() {
        String expectedValuesString = "1, 2, 3";
        String actualValuesString = paramService.getValuesString(testParams);
        Assertions.assertEquals(expectedValuesString,actualValuesString);
    }

    @Test
    void getTypesString() {
        String expectedTypesString = "one, two, three";
        String actualTypesString = paramService.getNamesString(testParams);
        Assertions.assertEquals(expectedTypesString,actualTypesString);
    }
}