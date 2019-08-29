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

    public void startProcessing(long messageToDBQueueId, long messageFromDBQueueId) {
        new Thread(
                () -> {
                    while (true) {
                        MyMessage message = messageService.getMessageFromQueue(messageToDBQueueId);
                        if (message != null) {
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
                                    messageFromDBQueueId,
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

