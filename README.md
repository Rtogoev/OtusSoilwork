# Описание

## Задание 1: проект maven

### Проект maven с модульной структурой
1) Создать аккаунт на github.com (если еще нет)
2) Создать репозиторий для домашних работ 
3) Сделать checkout репозитория на свой компьютер
4) Создайте локальный бранч hw01-maven
5) Создать проект maven
6) В проект добавьте последнюю версию зависимости
       
       <groupId>com.google.guava</groupId>
       <artifactId>guava</artifactId>
7) Создайте модуль hw01-maven
8) В модуле сделайте класс HelloOtus
9) В этом классе сделайте вызов какого-нибудь метода из guava
10) Добавьте нужный плагин maven и соберите "толстый-jar"
11) Убедитесь, что "толстый-jar" запускается.
12) Сделайте pull-request в gitHub
13) Ссылку на PR отправьте на проверку.

### Запуск
1) Открыть терминал в корне проекта и выполнить команду:

       cd /hw01-maven/target
       java -cp hw01-maven-1.0-SNAPSHOT-jar-with-dependencies.jar com.HelloOtus
       
## Задание 2: DIYarrayList

Написать свою реализацию ArrayList на основе массива.
     
     class DIYarrayList<T> implements List<T>{...}

Проверить, что на ней работают методы из java.util.Collections:

    Collections.addAll(Collection<? super T> c, T... elements)
    Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
    Collections.static <T> void sort(List<T> list, Comparator<? super T> c)

1) Проверяйте на коллекциях с 20 и больше элементами.
2) DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.

# Сборка
1) Открыть терминал в корне проекта и выполнить команду:

       mvn clean compile assembly:single package

# Об авторе

Студент:  
Тогоев Руслан  
rtogoev@megalabs.ru    