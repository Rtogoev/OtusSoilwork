package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;


public interface MessageService {
    void addMessageToQueue(MyMessage message);

    void setProcessor(MessageProcessor processor);
}
