package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public interface MessageService {
    void addMessageToQueue(int dbAddress, MyMessage message);

    int getDbAddress() throws IOException, TimeoutException;
}
