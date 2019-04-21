package com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionsTest {

    private static List<String> stringList;
    private DIYarrayList<String> stringDIYList;
    private String[] stringArray;

    @BeforeEach
    public void beforeEach() {
        stringDIYList = new DIYarrayList<>();
        stringList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            stringList.add(String.valueOf(i));
        }
        stringDIYList.addAll(stringList);
        stringArray = new String[1000];
        for (int i = 1000; i < 2000; i++) {
            stringArray[i - 1000] = String.valueOf(i);
        }
    }

    @Test
    void addAll() {
        int expectedSizeAfterAddAll = stringDIYList.size() + stringArray.length;
        Collections.addAll(stringDIYList, stringArray);
        Collections.addAll(stringList, stringArray);
        assertEquals(expectedSizeAfterAddAll, stringDIYList.size());
        for (int i = 0; i < expectedSizeAfterAddAll; i++) {
            assertEquals(stringDIYList.get(i), stringList.get(i));
        }
    }

    @Test
    void copy__DIYArrayList_To_DIYArrayList() {
        DIYarrayList<String> destinationStringDIYarrayList = new DIYarrayList<>(1000);
        Collections.copy(destinationStringDIYarrayList, stringDIYList);
        assertEquals(destinationStringDIYarrayList.size(), stringDIYList.size());
        for (int i = 0; i < stringDIYList.size(); i++) {
            assertEquals(stringDIYList.get(i), destinationStringDIYarrayList.get(i));
        }
    }

    @Test
    void copy__DIYArrayList_To_ArrayList() {

        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            stringArrayList.add(null);
        }

        Collections.copy(stringArrayList, stringDIYList);
        assertEquals(stringArrayList.size(), stringDIYList.size());
        for (int i = 0; i < stringDIYList.size(); i++) {
            assertEquals(stringDIYList.get(i), stringArrayList.get(i));
        }
    }

    @Test
    void copy___ArrayList_To_DIYArrayList() {
        DIYarrayList<String> destinationStringDIYarrayList = new DIYarrayList<>(1000);
        Collections.copy(destinationStringDIYarrayList, stringList);
        assertEquals(destinationStringDIYarrayList.size(), stringList.size());
        for (int i = 0; i < stringDIYList.size(); i++) {
            assertEquals(stringList.get(i), destinationStringDIYarrayList.get(i));
        }
    }
}