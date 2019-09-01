package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IdGenerator {
    private static Set<Long> generatedIdSet = new HashSet<>();

    public static long generate() {
        while (true) {
            long id = generateLong();
            if (generatedIdSet.add(id)) {
                return id;
            }
        }
    }

    private static long generateLong() {
        return (long) (Math.random() * 10000000);
    }
}
