package ru.otus.homework.services.testservices;

public class UserTestService {
    private Object testClass;

    public UserTestService(Object testClass) {
        this.testClass = testClass;
    }

    public String generateName() {
        return testClass.getClass().getSimpleName() + generateDigit();
    }

    public int generateAge() {
        return generateDigit();
    }

    private int generateDigit() {
        return (int) (Math.random() * 10000);
    }
}
