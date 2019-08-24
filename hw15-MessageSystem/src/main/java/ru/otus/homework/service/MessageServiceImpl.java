package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MessageFromDB;
import ru.otus.homework.model.MessageToDB;
import ru.otus.homework.model.MyMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    List<MyMessage> messages = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void addMessageToDB(MessageToDB messageToDB) {
        new Thread(
                () -> messages.add(messageToDB)
        ).start();
    }

    @Override
    public MessageToDB getMessageToDB() {
        Optional<MyMessage> first = messages.parallelStream()
                .filter(message -> message instanceof MessageToDB)
                .findFirst();
        if (first.isPresent()) {
            messages.remove(first.get());
            return (MessageToDB) first.get();
        }
        return null;
    }

    @Override
    public void addMessageFromDB(MessageFromDB messageFromDB) {
        new Thread(
                () -> messages.add(messageFromDB)
        ).start();
    }

    @Override
    public MessageFromDB getMessageFromDB() throws InterruptedException {
        Optional<MyMessage> first = messages.parallelStream()
                .filter(message -> message instanceof MessageFromDB)
                .findFirst();
        if (first.isPresent()) {
            messages.remove(first.get());
            return (MessageFromDB) first.get();
        }
        return null;
    }
}
