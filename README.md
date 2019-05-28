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

## Задание 6: Эмулятор банкомата
Написать эмулятор АТМ (банкомата).
              
Объект класса АТМ должен уметь:
 - принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
 - выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
 - выдавать сумму остатка денежных средств

Это задание не на алгоритмы, а на проектирование.
Поэтому оптимизировать выдачу не надо.

# Сборка
1) Открыть терминал в корне проекта и выполнить команду:

       mvn clean compile assembly:single package

# Об авторе

Студент:  
Тогоев Руслан  
rtogoev@megalabs.ru    