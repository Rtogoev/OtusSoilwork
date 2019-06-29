package ru.otus.homework.services.reflection;

import ru.otus.homework.annotations.Id;
import ru.otus.homework.services.database.Param;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionServiceImpl implements ReflectionService {
    @Override
    public <T> String getClassName(T objectData) {
        return objectData.getClass().getSimpleName();
    }

    @Override
    public <T> List<Param> getFieldsExceptIdAsParams(T objectData) throws IllegalAccessException {
        List<Param> params = new ArrayList<>();
        for (Field declaredField : objectData.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.getAnnotation(Id.class) == null) {
                params.add(
                        new Param(
                                declaredField.getName(),
                                String.valueOf(declaredField.get(objectData))
                        )
                );
            }
            declaredField.setAccessible(false);
        }
        return params;
    }
}