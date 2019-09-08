package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.util.UUID;


public interface MessageService {
    void addMessageToQueue(UUID queueOwnerId,MyMessage message);
    void addMessageProcessor(UUID address, MessageProcessor processor);
    UUID getDbAddress();
    UUID getFrontAddress();
    void addFrontAddress(UUID frontAddress);
    void addDbAddress(UUID dbAddress);
}
