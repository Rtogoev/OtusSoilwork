package ru.otus.homework.service;

import ru.otus.homework.model.User;


public interface MessageService {
    void addMessageToQueue(User value);

    void setProcessor(MessageProcessor processor);
}
