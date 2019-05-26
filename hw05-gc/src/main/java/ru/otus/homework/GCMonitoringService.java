package ru.otus.homework;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

public class GCMonitoringService {
    private static final CharSequence OLD = "Old";
    private static final CharSequence YOUNG = "Young";
    private static long lengthOld = 0;
    private static long countOld = 0;
    private static long lengthYoung = 0;
    private static long countYoung = 0;

    private GCMonitoringService() {
        // disable creation
    }

    public static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = gcbean.getName();
                    if (gcName.contains(OLD)) {
                        lengthOld = lengthOld + info.getGcInfo().getDuration();
                        countOld = countOld + 1;
                    }
                    if (gcName.contains(YOUNG)) {
                        lengthYoung = lengthYoung + info.getGcInfo().getDuration();
                        countYoung = countYoung + 1;
                    }

                    System.out.println(
                            "Количество сборок: " +
                                    " Young - " + countYoung +
                                    " Old - " + countOld + "; " +
                                    " Время, затраченное на сборки:" +
                                    " Young - " + lengthYoung +
                                    " Old - " + lengthOld
                    );
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
