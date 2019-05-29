package ru.otus.homework;

import org.junit.jupiter.api.Test;

public class SerialGC {
    /**
     * -Xms512m
     * -Xmx512m
     * -XX:+UseSerialGC
     * программа продержалась почти 10 минут, не было перегрузки проца, память иногда даже опускалась
     * пока что лучши сборщик
     * Количество сборок:  Young - 4 Old - 23;  Время, затраченное на сборки: Young - 719 Old - 19602
     */

    /*
     *
     * -Xms512m
     * -Xmx512m
     * -XX:+UseSerialGC
     * -XX:MaxGCPauseMillis=100000
     * программа продержалась почти 10 минут, не было перегрузки проца, память иногда даже опускалась
     * Количество сборок:  Young - 4 Old - 20;  Время, затраченное на сборки: Young - 710 Old - 17257
     */

    /*
     *
     * -Xms512m
     * -Xmx512m
     * -XX:+UseSerialGC
     * -XX:MaxGCPauseMillis=10
     *
     */

    /*
     *
     * -Xms2048m
     * -Xmx2048m
     * -XX:+UseSerialGC
     */

    /*
     *
     * -Xms5120m
     * -Xmx5120m
     * -XX:+UseSerialGC
     */

    /*
     *
     * -Xms20480m
     * -Xmx20480m
     * -XX:+UseSerialGC
     * /
     */

    @Test
    public void SerialGCTest() throws InterruptedException {
        GcStatisticService.createStatistic(
                250,
                50,
                1,
                "MarkSweepCompact",
                "Copy"
        );
    }
}
