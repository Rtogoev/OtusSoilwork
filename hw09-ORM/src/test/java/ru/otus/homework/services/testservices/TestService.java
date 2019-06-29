package ru.otus.homework.services.testservices;

public class TestService {
    private Object testClass;

    public TestService(Object testClass) {
        this.testClass = testClass;
    }

    public String generateTestName(){
        return  testClass.getClass().getSimpleName() + Math.random() * 10000;
    }

    public int generateAge() {
        return (int) Math.random();
    }
}
