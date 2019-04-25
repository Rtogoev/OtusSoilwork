package ru.otus.homework;

public class TestRunner {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        new TestRunner().run(AnnotationsTest.class);
    }

    public static void run(Class<AnnotationsTest> testClass) throws IllegalAccessException, InstantiationException {
        TestService.createTestSequence(testClass);
        TestService.executeTestSequence(testClass.newInstance());
    }
}
