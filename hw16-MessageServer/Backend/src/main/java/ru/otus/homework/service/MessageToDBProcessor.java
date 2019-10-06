package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;

@Service
public class MessageToDBProcessor implements MessageProcessor {
    private final MessageService messageService;
    private final NetworkService networkService;
    private DbService<User, Long> userService;
    @Value("${source.type}")
    private int sourceType;


    public MessageToDBProcessor(
            MessageService messageService,
            DbService<User, Long> userService,
            NetworkService networkService
    ) {
        this.messageService = messageService;
        this.userService = userService;
        this.networkService = networkService;
    }

    @PostConstruct
    void init() {
        messageService.setProcessor(this);
    }

    @Override
    public void process(MessageToDB messageToDB) throws IOException {
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
                new MessageFromDB(
                        networkService.getSourcePort(),
                        sourceType,
                        messageToDB.getDestinationPort(),
                        messageToDB.getDestinationType(),
                        new UserForm(
                                user.getId(),
                                user.getName(),
                                user.getAge(),
                                user.getAddressDataSet().getStreet(),
                                phone.toString()
                        )
                )
        );
    }
}

