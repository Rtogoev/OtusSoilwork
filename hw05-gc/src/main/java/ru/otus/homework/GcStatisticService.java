package ru.otus.homework;

import java.util.LinkedList;
import java.util.List;

public class GcStatisticService {
    private GcStatisticService() {
        // disable creation
    }

    private static List<Integer> integerList = new LinkedList<>();

    public static void createStatistic(
            int amountAdd,
            int amountRemove,
            int sleepMillis,
            String old,
            String young
            ) throws InterruptedException {
        GCMonitoringService.switchOnMonitoring(old,young);
        while (true) {
            for (int i = 0; i < amountAdd; i++) {
                integerList.add(i);
            }
            for (int i = 0; i < amountRemove; i++) {
                integerList.remove(0);
            }
            Thread.sleep(sleepMillis);
        }
    }
}
