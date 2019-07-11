package ru.otus.homework.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CacheImpl<T> implements Cache<T> {
    private final Map<T, SoftReference<T>> referenceSet;

    public CacheImpl() {
        referenceSet = new HashMap<>();
    }

    @Override
    public void put(T value) {
        referenceSet.put(value, new SoftReference<>(value));
    }

    @Override
    public T get(T key) {
        return referenceSet.get(key)
                .get();
    }

    @Override
    public int getHitCount() {
        return 0;
    }

    @Override
    public int getMissCount() {
        return 0;
    }

    @Override
    public void dispose() {

    }
}