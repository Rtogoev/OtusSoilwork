package ru.otus.homework.services.reflection;

import ru.otus.homework.services.database.Param;

import java.util.List;

public interface ReflectionService {
    <T> String getClassName(T objectData);

    <T> List<Param> getFieldsExceptIdAsParams(T objectData) throws IllegalAccessException;

}
