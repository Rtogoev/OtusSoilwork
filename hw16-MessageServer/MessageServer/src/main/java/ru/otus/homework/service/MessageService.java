package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;


public interface MessageService {
    void addMessageToQueue(long queueOwnerId,MyMessage message);
    void addMessageProcessor(long address,MessageProcessor processor);
    String getDbAddress();
    String getFrontAddress();
}
