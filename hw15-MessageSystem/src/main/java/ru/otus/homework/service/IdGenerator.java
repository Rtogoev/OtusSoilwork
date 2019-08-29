package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IdGenerator {
    private Set<Long> generatedIdSet = new HashSet<>();

    public long generate() {
        while (true) {
            long id = generateLong();
            if (generatedIdSet.add(id)) {
                return id;
            }
        }
    }

    private long generateLong() {
        return (long) (Math.random() * 10000000);
    }
}
