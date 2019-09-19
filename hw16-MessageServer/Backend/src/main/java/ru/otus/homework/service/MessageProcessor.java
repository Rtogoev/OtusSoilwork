package ru.otus.homework.service;

import ru.otus.homework.model.MessageToDB;

import java.io.IOException;

public interface MessageProcessor {
    void process(MessageToDB messageToDB) throws IOException;
}
