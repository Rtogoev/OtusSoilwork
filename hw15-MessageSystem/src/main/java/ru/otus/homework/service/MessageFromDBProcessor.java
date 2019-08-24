package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.MessageFromDB;
import ru.otus.homework.model.UserForm;

@Service
public class MessageFromDBProcessor {
    private final MessageService messageService;

    public MessageFromDBProcessor(MessageService messageService) {
        this.messageService = messageService;
    }

    public UserForm startProcessing() throws InterruptedException {
        while (true) {
            MessageFromDB messageFromDB = messageService.getMessageFromDB();
            if (messageFromDB != null) {
                return messageFromDB.getUserForm();
            }
        }
    }
}
