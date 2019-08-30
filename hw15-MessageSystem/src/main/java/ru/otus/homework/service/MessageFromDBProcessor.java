package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.MessageFromDB;
import ru.otus.homework.model.MyMessage;
import ru.otus.homework.model.UserForm;

import java.util.concurrent.LinkedBlockingDeque;

@Service
public class MessageFromDBProcessor {
    private final MessageService messageService;

    public MessageFromDBProcessor(MessageService messageService) {
        this.messageService = messageService;
    }

    public void startProcessing(long messageFromDBQueueId, LinkedBlockingDeque<UserForm> beforeSendBuffer) {
        new Thread(
                () -> {

                    while (true) {
                        MyMessage message = messageService.getMessageFromQueue(messageFromDBQueueId);
                        if (message != null) {
                            MessageFromDB messageFromDB = (MessageFromDB) message;
                            beforeSendBuffer.add(messageFromDB.getValue());
                        }
                    }
                }
        ).start();
    }
}
