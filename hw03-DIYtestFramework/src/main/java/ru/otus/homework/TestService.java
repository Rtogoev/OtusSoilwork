package ru.otus.homework;

import ru.otus.homework.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestService {

    private static Map<Class<? extends Annotation>, List<Method>> testSequenceMap = new HashMap<>();

    static {
        testSequenceMap.put(BeforeAll.class, new ArrayList<>());
        testSequenceMap.put(BeforeEach.class, new ArrayList<>());
        testSequenceMap.put(Test.class, new ArrayList<>());
        testSequenceMap.put(AfterEach.class, new ArrayList<>());
        testSequenceMap.put(AfterAll.class, new ArrayList<>());
    }

    private TestService() {
        // DISABLE CREATION
    }

    public static void createTestSequence(Class<?> testClass) {
        Arrays.stream(
                testClass.getDeclaredMethods()
        ).forEach(
                method -> testSequenceMap.keySet().stream()
                        .filter(annotation -> method.getAnnotation(annotation) != null)
                        .findFirst()
                        .map(annotation -> testSequenceMap.get(annotation).add(method))
        );

    }

    public static void executeTestSequence(Class<?> testClass) throws IllegalAccessException, InstantiationException {
        testSequenceMap.get(BeforeAll.class).forEach(
                beforeAllMethod -> {
                    try {
                        beforeAllMethod.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
        );
        final Object[] testClassInstanse = new Object[1];
        testSequenceMap.get(Test.class).forEach(
                testMethod -> {
                    try {
                        testClassInstanse[0] = testClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    testSequenceMap.get(BeforeEach.class).forEach(beforeEachMethod -> {
                        try {
                            beforeEachMethod.invoke(testClassInstanse[0]);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });

                    try {
                        testMethod.invoke(testClassInstanse[0]);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    testSequenceMap.get(AfterEach.class).forEach(afterEachMethod -> {
                        try {
                            afterEachMethod.invoke(testClassInstanse[0]);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
                }
        );

        testSequenceMap.get(AfterAll.class).forEach(
                afterAllMethod -> {
                    try {
                        afterAllMethod.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
