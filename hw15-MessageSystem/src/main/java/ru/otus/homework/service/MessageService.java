package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.util.UUID;


public interface MessageService {
    void addMessageToQueue(UUID queueOwnerId, MyMessage message);

    void addMessageProcessor(UUID address, MessageProcessor processor);

    UUID getAddress(long type);

    void addAddress(long type, UUID frontAddress);
}
