package ru.otus.homework;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        Server.PORT = 8080;
        Server.address = "127.0.0.1";
        server.start();
    }
}
