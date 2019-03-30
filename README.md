# Описание

## Проект maven с модульной структурой
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

# Сборка
1) Открыть терминал в корне проекта и выполнить команду:

       mvn clean compile assembly:single

# Запуск
1) Открыть терминал в корне проекта и выполнить команду:

       java -jar hw01-maven/target/*.jar
       
# Об авторе

Студент:  
Тогоев Руслан  
rtogoev@megalabs.ru    