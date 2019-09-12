package ru.otus.homework.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.homework.model.*;
import ru.otus.homework.service.MessageProcessor;
import ru.otus.homework.service.MessageService;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

@Controller
public class UserController implements MessageProcessor {
    private final MessageService messageService;
    private final SimpMessagingTemplate template;

    @Value("${source.port}")
    private String sourcePort;
    @Value("${source.type}")
    private String sourceType;
    @Value("${destination.port}")
    private String destinationPort;
    @Value("${destination.type}")
    private String destinationType;

    public UserController(
            MessageService messageService,
            SimpMessagingTemplate template
    ) {
        this.messageService = messageService;
        this.template = template;
        messageService.setProcessor(this);
    }

    @MessageMapping("/create")
    public void createUser(UserForm userForm) throws IOException, TimeoutException {
        messageService.addMessageToQueue(
                new MessageToDB(
                        sourcePort,
                        sourceType,
                        destinationPort,
                        destinationType,
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
        new Thread(
                () -> {
                    MessageFromDB messageFromDB = (MessageFromDB) message;
                    this.template.convertAndSend("/topic/response", messageFromDB.getValue());
                }
        ).start();
    }
}
