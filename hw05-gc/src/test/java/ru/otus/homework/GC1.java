package ru.otus.homework;

import org.junit.jupiter.api.Test;

class GC1 {

    /**
     *
     * -Xms512m
     * -Xmx512m
     * -XX:+UseG1GC
     * нагрузка на память растет примерно по 20 мегабайт в  секунду
     * Количество сборок:  Young - 29 Old - 4;  Время, затраченное на сборки: Young - 3308 Old - 4456
     *
     */

    /*
    *
    * -Xms512m
    * -Xmx512m
    * -XX:+UseG1GC
    * -XX:MaxGCPauseMillis=100000
    * нагрузка на память растет примерно по 5-10 мегабайт в  секунду
    * Количество сборок:  Young - 18 Old - 6;  Время, затраченное на сборки: Young - 2897 Old - 6685
    *
    * Программа продержалась чуть дольше на пике нагрузки
    */

    /*
     *
     * -Xms512m
     * -Xmx512m
     * -XX:+UseG1GC
     * -XX:MaxGCPauseMillis=10
     * нагрузка на память растет примерно по 20 мегабайт через каждые 2-3 секунды
     * Количество сборок:  Young - 35 Old - 5;  Время, затраченное на сборки: Young - 3573 Old - 5530
     * Программа продержалась дольше всех предыдущих случаев на пике нагрузки
     *
     */

    /*
    *
    * -Xms2048m
    * -Xmx2048m
    * -XX:+UseG1GC
     * пришлось поставить sleepMillis = 0, чтобы нагрузка на память росла быстрее, иначе ждать примерно 30 минут
     * нагрузка на память растет примерно по 100 мегабайт через каждые 0.5 секунды
     * Количество сборок:  Young - 33 Old - 5;  Время, затраченное на сборки: Young - 9732 Old - 22667
     * статистика по сборке около 8 минут не обновлялась, видима шестая Олд сборка была трудоемкой
     * Программа продержалась дольше всех предыдущих случаев на пике нагрузки
     * Примерно за 2 минуты память была почти заполнена, процессор сразу загрузился на 100 процентов
     * и в таком реиме программа держалась еще минут 10.
    */

    /*
     *
     * -Xms5120m
     * -Xmx5120m
     * -XX:+UseG1GC
     * пришлось поставить sleepMillis = 0, чтобы нагрузка на память росла быстрее, иначе ждать примерно 30 минут
     * нагрузка на память растет примерно по 300-500 мегабайт через каждые 1 секунды
     * Количество сборок:  Young - 29 Old - 2;  Время, затраченное на сборки: Young - 25922 Old - 28671
     * через минуту после старта теста статистика не обновлялась, видима третья сборка была трудоемкой
     * За 10 секунд память почти заполнилась, процессор был загружен на 100, жесткий диск почти на 60(своп)
     * и в таком реиме программа держалась еще минут 20. ноут стал очень сильно тормозить.
     * Я выключил - не стал дожидаться OOM
     */

    /*
     *
     * -Xms20480m
     * -Xmx20480m
     * -XX:+UseG1GC
     * пришлось поставить sleepMillis = 0, чтобы нагрузка на память росла быстрее, иначе дождаться OOM невозможно
     * нагрузка на память растет примерно по 800-1000 мегабайт через каждые 1 секунды
     * через 15 секунд после запуска теста, ноут завис так, что пришлось перегружать жестко по питанию. Такие вот выводы
     *
     * /
*/
    @Test
    public void GC1Test() throws InterruptedException {
        GcStatisticService.createStatistic(
                        250,
                        50,
                        1,
                "Old",
                "Young"
                );
    }
}