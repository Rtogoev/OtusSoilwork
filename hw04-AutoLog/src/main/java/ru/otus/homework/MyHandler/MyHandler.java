package ru.otus.homework.MyHandler;

import ru.otus.homework.MyClass.MyClassInterface;
import ru.otus.homework.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MyHandler implements InvocationHandler {
    private final MyClassInterface myClass;

    public MyHandler(MyClassInterface myClass) {
        this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println(
                    "executed method: " +
                            method.getName() +
                            ", params: " +
                            Arrays.toString(args)
                                    .replaceAll("\\[", "")
                                    .replaceAll("\\]", "")
            );
        }
        return method.invoke(myClass, args);
    }
}
