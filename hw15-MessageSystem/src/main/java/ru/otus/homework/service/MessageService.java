package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;


public interface MessageService {
    void addMessageToQueue(long queueOwnerId,MyMessage message);
    MyMessage getMessageFromQueue(long queueOwnerId);
}
