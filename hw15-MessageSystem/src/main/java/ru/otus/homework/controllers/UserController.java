package ru.otus.homework.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.homework.model.*;
import ru.otus.homework.service.IdGenerator;
import ru.otus.homework.service.MessageFromDBProcessor;
import ru.otus.homework.service.MessageService;
import ru.otus.homework.service.MessageToDBProcessor;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;

@Controller
public class UserController {
    private static Long messageToDBQueueId;
    private static Long messageFromDBQueueId;
    private final LinkedBlockingDeque<UserForm> beforeSendBuffer = new LinkedBlockingDeque<>();
    private final MessageService messageService;
    private final MessageToDBProcessor messageToDBProcessor;
    private final MessageFromDBProcessor messageFromDBProcessor;
    private final SimpMessagingTemplate template;

    public UserController(
            MessageService messageService,
            MessageToDBProcessor messageToDBProcessor,
            MessageFromDBProcessor messageFromDBProcessor,
            SimpMessagingTemplate template
    ) {
        this.messageService = messageService;
        this.messageToDBProcessor = messageToDBProcessor;
        this.messageFromDBProcessor = messageFromDBProcessor;
        this.template = template;
    }

    @PostConstruct
    void init() {
        if (messageFromDBQueueId == null) {
            messageFromDBQueueId = IdGenerator.generate();
        }
        if (messageToDBQueueId == null) {
            messageToDBQueueId = IdGenerator.generate();
        }
        messageToDBProcessor.startProcessing(messageToDBQueueId, messageFromDBQueueId);
        messageFromDBProcessor.startProcessing(messageFromDBQueueId, beforeSendBuffer);
        new Thread(
                () -> {
                    while (true) {
                        UserForm userForm = beforeSendBuffer.poll();
                        if (userForm != null) {
                            this.template.convertAndSend("/topic/response", userForm);
                        }
                    }
                }
        ).start();
    }

    @MessageMapping("/create")
    public void createUser(UserForm userForm) {
        messageService.addMessageToQueue(
                messageToDBQueueId,
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
}
