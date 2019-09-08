package ru.otus.homework.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.homework.model.*;
import ru.otus.homework.service.MessageProcessor;
import ru.otus.homework.service.MessageService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.UUID;

@Controller
public class UserController implements MessageProcessor {
    private static UUID address;
    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    public UserController(
            MessageService messageService,
            SimpMessagingTemplate template
    ) {
        this.messageService = messageService;
        this.template = template;
        address = UUID.randomUUID();
    }

    @PostConstruct
    void init() {
        messageService.addMessageProcessor(address, this);
        messageService.addFrontAddress(address);
    }

    @MessageMapping("/create")
    public void createUser(UserForm userForm) {
        messageService.addMessageToQueue(
                messageService.getDbAddress(),
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
    }

    @Override
    public void process(MyMessage message) {
        MessageFromDB messageFromDB = (MessageFromDB) message;
        this.template.convertAndSend("/topic/response", messageFromDB.getValue());
    }
}
