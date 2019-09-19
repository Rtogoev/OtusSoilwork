package ru.otus.homework.service;

import ru.otus.homework.model.User;

import java.io.IOException;


public interface MessageService {
    void addMessageToQueue(User value) throws IOException;

    void setProcessor(MessageProcessor processor);
}
