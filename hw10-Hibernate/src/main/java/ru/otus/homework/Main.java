package ru.otus.homework;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(
                "8080",
                "127.0.0.1",
                "hw10-Hibernate/src/main/resources/realm.properties",
                "hw10-Hibernate/src/main/resources/templates"
        );
        server.start();
    }
}
