package ru.otus.homework.MyClass;

public class MyClassImpl implements MyClassInterface {
    @Override
    public void methodForLog(int a, int b) {
        System.out.println("method for @Log");
    }

    @Override
    public void method(int a) {
        System.out.println("method");
    }
}
