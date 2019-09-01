package ru.otus.homework.service;

import ru.otus.homework.model.MyMessage;

public interface MessageProcessor {
    void process(MyMessage myMessage);
}
