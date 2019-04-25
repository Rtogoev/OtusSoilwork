package ru.otus.homework;

import java.lang.reflect.Method;
import java.util.ArrayList;
public class TestService<T> {
//
//    private static ArrayList<Object> testAnnotations = Arrays.asList(
//            BeforeAll.class,
//            AfterAll.class,
//            BeforeEach.class,
//            Test.class,
//            AfterEach.class
//    );
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
    }

    public static void executeTestSequence() {
    }
}
