package ru.otus.homework;

import ru.otus.homework.MyClass.MyClassImpl;
import ru.otus.homework.MyClass.MyClassInterface;
import ru.otus.homework.MyHandler.MyHandler;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        (
                (MyClassInterface) Proxy.newProxyInstance(
                        Main.class.getClassLoader(),
                        new Class<?>[]{MyClassInterface.class},
                        new MyHandler(new MyClassImpl())
                )
        ).method(1, 2);
    }
}
