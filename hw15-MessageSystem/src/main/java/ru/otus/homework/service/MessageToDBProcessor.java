package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.*;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class MessageToDBProcessor implements MessageProcessor {
    private static UUID address;
    private final MessageService messageService;
    private DbService<User, Long> userService;

    @Value("${frontend.type}")
    private long receiverType;
    @Value("${backend.type}")
    private long type;

    public MessageToDBProcessor(MessageService messageService, DbService<User, Long> userService) {
        this.messageService = messageService;
        this.userService = userService;
        address = UUID.randomUUID();
    }

    @PostConstruct
    void init() {
        messageService.addMessageProcessor(address, this);
        messageService.addAddress(type, address);
    }

    @Override
    public void process(MyMessage message) {
        MessageToDB messageToDB = (MessageToDB) message;
        User user = messageToDB.getValue();
        try {
            userService.create(user);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        StringBuilder phone = new StringBuilder();
        for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
            phone.append(phoneDataSet.getNumber());
        }
        messageService.addMessageToQueue(
                messageService.getAddress(receiverType),
                new MessageFromDB(
                        new UserForm(
                                user.getId().toString(),
                                user.getName(),
                                String.valueOf(user.getAge()),
                                user.getAddressDataSet().getStreet(),
                                phone.toString()
                        )
                )
        );
    }
}

