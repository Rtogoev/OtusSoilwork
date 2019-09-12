package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

import java.nio.channels.SocketChannel;


public interface MessageProcessor {
    void process(SocketChannel socketChannel, MyMessage myMessage);
}
