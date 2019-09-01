package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;


public interface MessageService {
    void addMessageToQueue(long queueOwnerId,MyMessage message);
    void addMessageProcessor(long address,MessageProcessor processor);
    long getDbAddress();
    long getFrontAddress();
    void setFrontAddress(long frontAddress);
    void setDbAddress(long dbAddress);
}
