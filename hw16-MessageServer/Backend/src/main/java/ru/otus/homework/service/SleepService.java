package ru.otus.homework.service;

public class SleepService {
    // todo скопировать этот класс во все модули
    public static void sleep(Long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
