package ru.otus.homework.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        SoftReference<T> t = referenceSet.get(key);
        if (t == null) {
            return null;
        }
        return t.get();
    }

    @Override
    public void remove(T key) {
        referenceSet.remove(key);
    }

    @Override
    public Set<T> getAll() {
        return referenceSet.keySet();
    }
}