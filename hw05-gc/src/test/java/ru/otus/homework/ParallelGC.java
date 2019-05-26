package ru.otus.homework;

import java.util.LinkedList;
import java.util.List;

/*
-Xms512m
        -Xmx512m
        -XX:+UseG1GC
        */

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/
public class ParallelGC {
    private static List<Integer> integerList = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        GCMonitoringService.switchOnMonitoring();
        while (true) {
            for (int i = 0; i < 250; i++) {
                integerList.add(i);
            }
            for (int i = 0; i < 50; i++) {
                integerList.remove(0);
            }
            Thread.sleep(1);
        }
    }
}
