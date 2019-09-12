package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.nio.channels.SocketChannel;


public interface MessageService {

    void addMessageToQueue(Long queueOwnerId, MyMessage message);

    void addSocketChannel(Long address, SocketChannel socketChannel);

    Long getAddress(long type);

    void addAddress(long type, Long frontAddress);
}
