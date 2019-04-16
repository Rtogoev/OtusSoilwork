package com;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DIYarrayListTest {
    private static final String ZERO_ELEMENT = "0";
    private static List<String> stringList;
    /*
            Collections.addAll(Collection<? super T> c, T... elements)
            Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
            Collections.static <T> void sort(List<T> list, Comparator<? super T> c)

        1) Проверяйте на коллекциях с 20 и больше элементами.
        2) DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
        3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.
        * */
    private List<String> stringDIYList;

    @BeforeEach
    public void init() {
        stringDIYList = new DIYarrayList<>();
        stringList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stringList.add(String.valueOf(i));
        }
    }

    @Test
    void size_Null() {
        assertEquals(0, stringDIYList.size());
    }

    @Test
    void size_notNull() {
        stringDIYList.add(ZERO_ELEMENT);
        assertEquals(1, stringDIYList.size());
    }

    @Test
    void isEmpty() {

    }

    @Test
    void contains() {

    }

    @Test
    void iterator() {

    }

    @Test
    void toArray() {

    }

    @Test
    void toArray1() {

    }

    @Test
    void add() {
        stringDIYList.add(ZERO_ELEMENT);
        stringDIYList.add("1");
        assertEquals(ZERO_ELEMENT, stringDIYList.get(0));
        assertEquals("1", stringDIYList.get(1));
    }

    @Test
    void remove() {

    }

    @Test
    void containsAll() {

    }

    @Test
    void addAll_stringDIYLitIsNotNull() {
        stringDIYList.add(ZERO_ELEMENT);
        int expectedSizeAfterAddAll = stringDIYList.size() + stringList.size();
        stringDIYList.addAll(stringList);
        assertEquals(expectedSizeAfterAddAll, stringDIYList.size());
        assertEquals(ZERO_ELEMENT, stringDIYList.get(0));
        for (int i = 0; i < stringList.size(); i++) {
            assertEquals(
                    String.valueOf(i), stringDIYList.get(i + 1)
            );
        }
    }

    @Test
    void addAll_stringDIYLitIsNull() {
        stringDIYList = new DIYarrayList<>();
        int expectedSizeAfterAddAll = stringList.size();
        stringDIYList.addAll(stringList);
        assertEquals(expectedSizeAfterAddAll, stringDIYList.size());
        assertEquals(ZERO_ELEMENT, stringDIYList.get(0));
        for (int i = 0; i < stringList.size(); i++) {
            assertEquals(
                    String.valueOf(i), stringDIYList.get(i + 1)
            );
        }

    }

    @Test
    void removeAll() {

    }

    @Test
    void retainAll() {

    }

    @Test
    void clear() {

    }

    @Test
    void get() {

    }

    @Test
    void set() {

    }

    @Test
    void add1() {

    }

    @Test
    void remove1() {

    }

    @Test
    void indexOf() {

    }

    @Test
    void lastIndexOf() {

    }

    @Test
    void listIterator() {

    }

    @Test
    void listIterator1() {

    }

    @Test
    void subList() {

    }
}