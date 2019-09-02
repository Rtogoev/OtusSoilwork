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
       

### Сборка
1) Открыть терминал в корне проекта и выполнить команду:

       mvn clean compile assembly:single package


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

## Задание 3: Свой тестовый фреймворк.
Написать свой тестовый фреймворк.
Поддержать свои аннотации @Test, @Before, @After.

Запускать вызовом статического метода с именем класса с тестами.

Т.е. надо сделать:
1) создать три аннотации - @Test, @Before, @After.
2) Создать класс-тест, в котором будут методы, отмеченные аннотациями.
3) Создать "запускалку теста". На вход она должна получать имя класса с тестами.
4) "Запускалка" должна в классе-тесте найти и запустить методы, отмеченные аннотациями.
5) Алгоримт запуска должен быть такой:

   * метод Before
   * методы Test
   * метод After

   для каждой такой "тройки" надо создать СВОЙ объект класса-теста.
6) Исключение в одном тесте не должно прерывать весь процесс тестирования.


## Задание 4: Автомагическое логирование.
Разработайте такой функционал:  
метод класса можно пометить самодельной аннотацией @Log, например, так:  

    class TestLogging {
       @Log
       public void calculation(int param) {};
    }

При вызове этого метода "автомагически" в консоль должны логироваться значения параметров.  
Например так.  

     class Demo {
        public void action() {
        new TestLogging().calculation(6); 
      }
    }

В консоле дожно быть:
executed method: calculation, param: 6

Обратите внимание: явного вызова логирования быть не должно.

## Задание 5: Сравнение разных сборщиков мусора

Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа
(young, old) и время которое ушло на сборки в минуту.

Добиться OutOfMemory в этом приложении через медленное подтекание по памяти 
(например добавлять элементы в List и удалять только половину).

Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало 
с OOM примерно через 5 минут после начала работы.

Собрать статистику (количество сборок, время на сборки) по разным GC.

!!! Сделать выводы !!! 
ЭТО САМАЯ ВАЖНАЯ ЧАСТЬ РАБОТЫ:
Какой gc лучше и почему?


## Задание 6: Эмулятор банкомата
Написать эмулятор АТМ (банкомата).
              
Объект класса АТМ должен уметь:
 - принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
 - выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
 - выдавать сумму остатка денежных средств

Это задание не на алгоритмы, а на проектирование.
Поэтому оптимизировать выдачу не надо.

## Задание 7: Департамент ATM
Написать приложение ATM Департамент:

1) Департамент может содержать несколько ATM.
2) Департамент может собирать сумму остатков со всех ATM.
3) Департамент может инициировать событие – восстановить состояние всех
ATM до начального (начальные состояния у разных ATM могут быть
разными).  

Это тренировочное задание на применение паттернов.
Попробуйте использовать как можно больше.

## Задание 8: json object writer
Напишите свой json object writer (object to JSON string) аналогичный gson на основе javax.json.

Поддержите:
- массивы объектов и примитивных типов
- коллекции из стандартный библиотеки.

## Задание 9: Самодельный ORM
Работа должна использовать базу данных H2.
Создайте в базе таблицу User с полями:

• id bigint(20) NOT NULL auto_increment
• name varchar(255)
• age int(3)

Создайте свою аннотацию @Id

Создайте класс User (с полями, которые соответствуют таблице, поле id отметьте аннотацией).

Напишите JdbcTemplate, который умеет работать с классами, в которых есть поле с аннотацией @Id.
JdbcTemplate должен сохранять объект в базу и читать объект из базы.
Имя таблицы должно соответствовать имени класса, а поля класса - это колонки в таблице.

Методы JdbcTemplate'а:
void create(T objectData);
void update(T objectData);
void createOrUpdate(T objectData); // опционально.
<T> T load(long id, Class<T> clazz);

Проверьте его работу на классе User.

Метод createOrUpdate - необязательный.
Он должен "проверять" наличие объекта в таблице и создавать новый или обновлять.

Создайте еще одну таблицу Account:
• no bigint(20) NOT NULL auto_increment
• name varchar(255)
• rest number

Создайте для этой таблицы класс Account и проверьте работу JdbcTemplate на этом классе.

## Задание 10: Использование Hibernate
Работа должна использовать базу данных H2.

Возьмите за основу предыдущее ДЗ (Самодельный ORM)  
и реализуйте функционал сохранения и чтения объекта User через Hibernate.  
(Рефлейсия больше не нужна)  
Конфигурация Hibernate должна быть вынесена в файл.  

Добавьте в User поля:
адрес (OneToOne)
 
    class AddressDataSet {
       private String street;
    }
и телефон (OneToMany)
  
    class PhoneDataSet {
       private String number;
    }

Разметьте классы таким образом, чтобы при сохранении/чтении объека User каскадно сохранялись/читались вложенные объекты.

## Задание 11: Cache engine
Напишите свой cache engine с soft references.  
Добавьте кэширование в DBService из задания про Hibernate ORM  

## Задание 12: Веб сервер
Встроить веб сервер в приложение из ДЗ про Hibernate ORM.  
Сделать админскую страницу, на которой админ должен авторизоваться.  
На странице должны быть доступны следующие функции:
- создать пользователя
- получить список пользователей

## Задание 13: Приложение с IoC контейнером
Собрать war для приложения из предыдущего ДЗ.   
Создавать кэш и DBService как Spring beans, передавать (\inject) их в сервлеты.   
Запустить веб приложение во внешнем веб сервере. 

## Задание 14: Последовательность чисел
Два потока печатают числа от 1 до 10, потом от 10 до 1.  
Надо сделать так, чтобы числа чередовались, т.е. получился такой вывод:  
Поток 1:1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3 4....   
Поток 2: 1 2 3 4 5 6 7 8 9 10 9 8 7 6 5 4 3 2 1 2 3....   

## Задание 15: MessageSystem

Пояснения к ДЗ-15.  
Коллеги, краткое описание алгоримта работы:  
Действующие лица:  
- FrontService (FS)  
- MessageService (MS)  
- DatabaseService (DS)  
DS и FS взаимодействуют только через MS.  
Должно быть так:  
1) Пользователь в браузере нажимает кнопку "создать пользователя"
2) В обработчик webSocket'а приходит запрос на создание пользователя.
3) Обработчик webSocket'а создает сообщение "создать такого-то юзера"
4) Сообщение уходить в MS.
5) MS должен понять, что это сообщение для DB и отправить сообщение в DB.
6) DB должен получить сообщение, создать юзера и сформировать ответное сообщение
7) MS должен получить сообщение от DB и переслать его в FS
8) FS должен получить сообщение и отправить ответ в браузер.  

Получается, что между пунктами 1 и 8 есть временной лаг,  
т.е. сообщения должны быть ассинхронными, а проще всего это сделать на WebSockekt'ах.  


## Задание 16: MessageServer

Cервер из предыдущего ДЗ про MessageSystem разделить на три приложения:

 * MessageServer
 * Frontend
 * DBServer
 
Запускать Frontend и DBServer из MessageServer.  
Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами.  
Пересылать сообщения с Frontend на DBService через MessageServer.   

Запустить приложение с двумя серверами фронтенд и двумя серверами баз данных на разных портах.  
Если у вас запуск веб приложения в контейнере, то MessageServer может копировать root.war в контейнеры при старте

### Запуск
1) Зайти в папку Открыть терминал в корне проекта и выполнить команду:

       java -jar Имя_jar_файла_без_original_в_конце
       

### Сборка
1) Зайти в в папки Backend, Frontend, MessageService. Открыть терминал и выполнить команду:

       mvn package


# Об авторе

Студент:  
Тогоев Руслан  
rtogoev@megalabs.ru    