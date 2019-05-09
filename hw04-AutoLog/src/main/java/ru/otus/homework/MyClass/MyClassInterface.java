package ru.otus.homework.MyClass;

import ru.otus.homework.annotations.Log;

public interface MyClassInterface {
    @Log
    void methodForLog(int a, int b);

    void method(int a);
}
