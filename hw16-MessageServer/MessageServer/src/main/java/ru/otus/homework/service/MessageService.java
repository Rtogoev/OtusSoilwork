package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.nio.channels.ServerSocketChannel;


public interface MessageService {

    void addMessageToQueue(Long queueOwnerId, MyMessage message);

    ServerSocketChannel getAddress(long type);

    void addAddress(long type, ServerSocketChannel socketChannel);
}
