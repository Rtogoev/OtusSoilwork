package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.io.IOException;


public interface MessageService {
    void addMessageToQueue(MyMessage message) throws IOException;

    void setProcessor(MessageProcessor processor);
}
