package ru.otus.homework.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.homework.model.*;
import ru.otus.homework.service.MessageService;

import java.util.Collections;

@Controller
public class UserController {

    private final MessageService messageService;

    public UserController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/create")
    @SendTo("/topic/response")
    public UserForm createUser(UserForm userForm) {
        User user = new User(
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
        );
        messageService.addMessageToDB(
                new MessageToDB(user)
        );
        return userForm;
    }
}
