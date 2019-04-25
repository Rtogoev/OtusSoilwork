package ru.otus.homework;

public class TestRunner {
    public static void main(String[] args) {
        new TestRunner().run(AnnotationsTest.class);
    }

    public void run(Class<AnnotationsTest> testClass) {
        TestService.createTestSequence(testClass);
        TestService.executeTestSequence();
    }
}
