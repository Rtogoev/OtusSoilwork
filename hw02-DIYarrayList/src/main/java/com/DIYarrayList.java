package com;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DIYarrayList<T> implements List<T> {
    /*
        e*
        Collections.addAll(Collection<? super T> c, T... elements)
        Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        *
         *  */
    private T[] thisArray;

    public DIYarrayList() {
        thisArray = (T[]) new Object[0];
    }

    @Override
    public int size() {
        return thisArray.length;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();

    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean add(T t) {
        if (thisArray == null || thisArray.length == 0) {
            thisArray = (T[]) new Object[1];
            thisArray[0] = t;
            return true;
        }
        T[] newArray = (T[]) new Object[1 + thisArray.length];
        for (int i = 0; i < thisArray.length; i++) {
            newArray[i] = thisArray[i];
        }
        newArray[newArray.length - 1] = t;
        thisArray = newArray;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null || c.size() == 0) {
            return false;
        }
        T[] cArray = (T[]) c.toArray();
        T[] newArray = (T[]) new Object[c.size() + thisArray.length];
        for (int i = 0; i < thisArray.length; i++) {
            newArray[i] = thisArray[i];
        }
        for (int i = 0; i < c.size(); i++) {
            newArray[i + thisArray.length] = cArray[i];
        }
        thisArray = newArray;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();

    }

    @Override
    public T get(int index) {
        return thisArray[index];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();

    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();

    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();

    }
}
