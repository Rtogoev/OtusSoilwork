package ru.otus.homework.service;

import ru.otus.homework.model.MessageToDB;

public interface MessageProcessor {
    void process(MessageToDB messageToDB);
}
