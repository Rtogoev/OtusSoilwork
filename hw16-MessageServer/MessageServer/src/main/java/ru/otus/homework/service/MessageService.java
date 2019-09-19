package ru.otus.homework.service;

import ru.otus.homework.model.Connection;
import ru.otus.homework.model.MyMessage;


public interface MessageService {

    void addMessageToQueue(Long queueOwnerId, MyMessage message);

    Connection getAddress(long type);

    void addAddress(long type, Connection connection);
}
