package ru.otus.homework.MyClass;

public class MyClassImpl implements MyClassInterface {
    @Override
    public void method(int a, int b) {
        System.out.println("method invoking");
    }
}
