package ru.otus.homework.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.homework.model.*;
import ru.otus.homework.service.MessageFromDBProcessor;
import ru.otus.homework.service.MessageService;
import ru.otus.homework.service.MessageToDBProcessor;

import java.util.Collections;

@Controller
public class UserController {

    private final MessageService messageService;
    private final MessageToDBProcessor messageToDBProcessor;
    private final MessageFromDBProcessor messageFromDBProcessor;

    public UserController(
            MessageService messageService,
            MessageToDBProcessor messageToDBProcessor,
            MessageFromDBProcessor messageFromDBProcessor
    ) {
        this.messageService = messageService;
        this.messageToDBProcessor = messageToDBProcessor;
        this.messageFromDBProcessor = messageFromDBProcessor;
    }

    @MessageMapping("/create")
    @SendTo("/topic/response")
    public UserForm createUser(UserForm userForm) throws InterruptedException {
        messageService.addMessageToDB(
                new MessageToDB(
                        new User(
                                userForm.getName(),
                                Integer.parseInt(
                                        userForm.getAge()
                                ),
                                new AddressDataSet(
                                        userForm.getAddress()
                                ),
                                Collections.singleton(
                                        new PhoneDataSet(
                                                userForm.getPhone()
                                        )
                                )
                        )
                )
        );
        messageToDBProcessor.startProcessing();
        return messageFromDBProcessor.startProcessing();
    }
}
