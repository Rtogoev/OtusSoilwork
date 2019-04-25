package ru.otus.homework;

import ru.otus.homework.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class TestService<T> {

    private static ArrayList<Class<? extends Annotation>> testAnnotations =
            (ArrayList<Class<? extends Annotation>>)
                    Arrays.asList(
                            BeforeAll.class,
                            AfterAll.class,
                            BeforeEach.class,
                            Test.class,
                            AfterEach.class
                    );
    private ArrayList<Method> afterAllMethods = new ArrayList<>();
    private ArrayList<Method> beforeEachMethods = new ArrayList<>();
    private ArrayList<Method> afterEachMethods = new ArrayList<>();
    private ArrayList<Method> testMethods = new ArrayList<>();
    private ArrayList<Method> beforeAllMethods = new ArrayList<>();

    public static void createTestSequence(Class<?> testClass) {
        for (Method method : testClass.getMethods()) {
            sortIfAnnotated(method);
        }
    }

    private static void sortIfAnnotated(Method method) {
        for (Class<? extends Annotation> testAnnotation : testAnnotations) {
            if (method.getAnnotation(testAnnotation) == null) {
            }
        }
    }

    public static void executeTestSequence() {
    }
}
