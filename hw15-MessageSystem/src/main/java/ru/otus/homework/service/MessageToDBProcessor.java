package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.*;

import java.sql.SQLException;

@Service
public class MessageToDBProcessor {
    private final MessageService messageService;
    private DbService<User, Long> userService;

    public MessageToDBProcessor(MessageService messageService, DbService<User, Long> userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    public void startProcessing() {
        new Thread(
                () -> {
                    while (true) {
                        MessageToDB messageToDB = messageService.getMessageToDB();
                        if (messageToDB != null) {
                            User user = messageToDB.getUser();
                            try {
                                userService.create(user);
                            } catch (SQLException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            StringBuilder phone = new StringBuilder();
                            for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
                                phone.append(phoneDataSet.getNumber());
                            }
                            messageService.addMessageFromDB(
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
                            return;
                        }
                    }
                }
        ).start();
    }
}

