package ru.otus.homework.services.reflection;

import ru.otus.homework.services.database.Param;

import java.sql.ResultSet;
import java.util.List;

public interface ReflectionService {
    <T> String getClassName(T objectData);

    <T> List<Param> getFieldsExceptIdAsParams(T objectData) throws IllegalAccessException;

    <T> T getInstanse(Class clazz, ResultSet resultSet) throws Exception;

    <T> long getId(T objectData) throws IllegalAccessException;
}
