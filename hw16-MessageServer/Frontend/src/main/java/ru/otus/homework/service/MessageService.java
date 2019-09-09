package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public interface MessageService {
    void addMessageToQueue(String dbAddress, MyMessage message);

    String getDbAddress() throws IOException, TimeoutException;
}
