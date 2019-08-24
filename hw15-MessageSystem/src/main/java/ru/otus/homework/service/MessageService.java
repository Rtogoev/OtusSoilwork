package ru.otus.homework.service;

import ru.otus.homework.model.MessageFromDB;
import ru.otus.homework.model.MessageToDB;


public interface MessageService {

    void addMessageToDB(MessageToDB messageToDB);

    void addMessageFromDB(MessageFromDB messageToDB);

    MessageToDB getMessageToDB();

    MessageFromDB getMessageFromDB() throws InterruptedException;
}
